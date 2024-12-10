package com.cerv1no.ecommerce.service;


import com.cerv1no.ecommerce.model.Order;
import com.cerv1no.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("spring.mail.username")
    private String fromEmail;

    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(fromEmail);
        email.setTo(order.getUser().getEmail());
        email.setSubject("Order Confirmation");
        email.setText("Order with ID " + order.getId() + " has been successfully placed.");
        javaMailSender.send(email);
    }

    public void sendConfirmationCode(User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(fromEmail);
        email.setTo(user.getEmail());
        email.setSubject("Confirm your email");
        email.setText("Your confirmation code: " + user.getConfirmationCode());
        javaMailSender.send(email);
    }
}
