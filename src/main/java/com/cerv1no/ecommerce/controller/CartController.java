package com.cerv1no.ecommerce.controller;

import com.cerv1no.ecommerce.model.User;
import com.cerv1no.ecommerce.service.CartService;
import com.cerv1no.ecommerce.dto.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.getCart(((User)userDetails).getId()));
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> addToCart(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long productId,
                                             @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.createCart(((User)userDetails).getId(), productId, quantity));
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCart(((User)userDetails).getId());
        return ResponseEntity.noContent().build();
    }
}
