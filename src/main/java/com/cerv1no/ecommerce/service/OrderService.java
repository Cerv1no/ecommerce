package com.cerv1no.ecommerce.service;

import com.cerv1no.ecommerce.dto.CartDto;
import com.cerv1no.ecommerce.dto.OrderDto;
import com.cerv1no.ecommerce.exception.ResourceNotFoundException;
import com.cerv1no.ecommerce.mapper.CartMapper;
import com.cerv1no.ecommerce.mapper.OrderMapper;
import com.cerv1no.ecommerce.model.*;
import com.cerv1no.ecommerce.repositories.OrderRepository;
import com.cerv1no.ecommerce.repositories.ProductRepository;
import com.cerv1no.ecommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public OrderService(CartService cartService, OrderRepository orderRepository, UserRepository userRepository,
                        EmailService emailService, OrderMapper orderMapper, CartMapper cartMapper,
                        ProductRepository productRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.orderMapper = orderMapper;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
    }

    public List<OrderDto> getAllOrders() {
        return orderMapper.toOrderDtos(orderRepository.findAll());
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderMapper.toOrderDtos(orderRepository.findByUserId(userId));
    }

    @Transactional
    public OrderDto createOrder(Long userId, String address, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isEmailConfirmed())
            throw new IllegalStateException("User email is not confirmed. Confirm email first.");

        CartDto cartDto = cartService.getCart(userId);
        Cart cart = cartMapper.toEntity(cartDto);
        if (cart.getItems().isEmpty())
            throw new IllegalStateException("Cart is empty");

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setStatus(Order.OrderStatus.PREPARING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = createOrderItems(cart, order);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        try {
            emailService.sendOrderConfirmationEmail(savedOrder);
        } catch (Exception e) {
            logger.error("Error sending email", e);
        }
        return orderMapper.toDto(savedOrder);
    }

    private List<OrderItem> createOrderItems(Cart cart, Order order) {
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = productRepository.findById(cartItem.getProduct().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                    if (product.getQuantity() == null)
                        throw new IllegalStateException("Product quantity is null");
                    if (product.getQuantity() < cartItem.getQuantity())
                        throw new IllegalStateException("Not enough quantity for product " + product.getName());

                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    productRepository.save(product);

                    return new OrderItem(null, order, product, cartItem.getQuantity(), product.getPrice());
                }).toList();
    }

    public OrderDto updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

}
