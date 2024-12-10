package com.cerv1no.ecommerce.service;

import com.cerv1no.ecommerce.dto.CartDto;
import com.cerv1no.ecommerce.exception.InsufficientStockException;
import com.cerv1no.ecommerce.exception.ResourceNotFoundException;
import com.cerv1no.ecommerce.mapper.CartMapper;
import com.cerv1no.ecommerce.model.Cart;
import com.cerv1no.ecommerce.model.CartItem;
import com.cerv1no.ecommerce.model.Product;
import com.cerv1no.ecommerce.model.User;
import com.cerv1no.ecommerce.repositories.CartRepository;
import com.cerv1no.ecommerce.repositories.ProductRepository;
import com.cerv1no.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    public CartDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cartMapper.toDto(cart);
    }

    public CartDto createCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getQuantity() < quantity)
            throw new InsufficientStockException("Not enough quantity");
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, user, new ArrayList<>()));
        Optional<CartItem> cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst();

        if (cartItem.isPresent())
            cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
        else
            cart.getItems().add(new CartItem(null, cart, product, quantity));

        return cartMapper.toDto(cartRepository.save(cart));
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
