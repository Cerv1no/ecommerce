package com.cerv1no.ecommerce.service;

import com.cerv1no.ecommerce.dto.ChangePasswordRequest;
import com.cerv1no.ecommerce.exception.ResourceNotFoundException;
import com.cerv1no.ecommerce.model.User;
import com.cerv1no.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        user.setConfirmationCode(generateConfirmationCode());
        user.setEmailConfirmed(false);
        emailService.sendConfirmationCode(user);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        User user = getUserByEmail(email);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Old password is incorrect");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void confirmEmail(String email, String confirmationCode) {
        User user = getUserByEmail(email);
        if (user.getConfirmationCode().equals(confirmationCode)){
            user.setEmailConfirmed(true);
            user.setConfirmationCode(null);
            userRepository.save(user);
        }
        else
            throw new BadCredentialsException("Confirmation code is incorrect");
    }

    private String generateConfirmationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }
}
