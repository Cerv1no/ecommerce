package com.cerv1no.ecommerce.controller;

import com.cerv1no.ecommerce.dto.OrderDto;
import com.cerv1no.ecommerce.model.Order;
import com.cerv1no.ecommerce.model.User;
import com.cerv1no.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDto>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(((User)userDetails).getId()));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDto> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam String address,
                                                @RequestParam String phoneNumber) {
        return ResponseEntity.ok(orderService.createOrder(((User)userDetails).getId(), address, phoneNumber));
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestParam Order.OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

}
