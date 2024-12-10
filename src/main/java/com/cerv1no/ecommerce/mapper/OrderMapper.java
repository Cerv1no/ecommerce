package com.cerv1no.ecommerce.mapper;

import com.cerv1no.ecommerce.dto.OrderDto;
import com.cerv1no.ecommerce.dto.OrderItemDto;
import com.cerv1no.ecommerce.model.Order;
import com.cerv1no.ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "items")
    OrderDto toDto(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "items", source = "orderItems")
    Order toEntity(OrderDto orderDto);

    List<OrderDto> toOrderDtos(List<Order> orders);
    List<Order> toOrderEntities(List<OrderDto> orderDtos);

    @Mapping(target = "productId", source = "product.id")
    OrderItemDto toDto(OrderItem orderItem);
    @Mapping(target = "product.id", source = "productId")
    OrderItem toEntity(OrderItemDto orderItemDto);

    List<OrderItemDto> toOrderItemDtos(List<OrderItem> orderItems);
    List<OrderItem> toOrderItemDtoEntities(List<OrderItemDto> orderItemDtos);
}
