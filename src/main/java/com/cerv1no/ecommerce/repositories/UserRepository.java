package com.cerv1no.ecommerce.repositories;

import com.cerv1no.ecommerce.model.Cart;
import com.cerv1no.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}