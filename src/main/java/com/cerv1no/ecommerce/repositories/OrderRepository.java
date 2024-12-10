package com.cerv1no.ecommerce.repositories;

import com.cerv1no.ecommerce.model.Cart;
import com.cerv1no.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}