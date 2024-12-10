package com.cerv1no.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Product description is required")
    private String description;
    @Positive(message = "Product quantity must be greater than 0")
    private Integer quantity;
    @PositiveOrZero(message = "Product price must be greater than 0")
    private BigDecimal price;
    private List<CommentDto> comments;
    private String imageUrl;

    public @NotBlank(message = "Product name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Product name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Product description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Product description is required") String description) {
        this.description = description;
    }

    public @Positive(message = "Product quantity must be greater than 0") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Positive(message = "Product quantity must be greater than 0") Integer quantity) {
        this.quantity = quantity;
    }

    public @PositiveOrZero(message = "Product price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@PositiveOrZero(message = "Product price must be greater than 0") BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
