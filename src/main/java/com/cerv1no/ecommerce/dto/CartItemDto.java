package com.cerv1no.ecommerce.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    @PositiveOrZero(message = "Quantity must be greater than 0")
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
