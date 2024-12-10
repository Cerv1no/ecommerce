package com.cerv1no.ecommerce.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private Long productId;
    @PositiveOrZero(message = "Quantity must be greater than 0")
    private Integer quantity;
    @PositiveOrZero(message = "Price must be greater than 0")
    private BigDecimal price;
}
