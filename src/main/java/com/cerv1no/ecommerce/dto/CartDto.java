package com.cerv1no.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items;
}
