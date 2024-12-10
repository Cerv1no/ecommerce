package com.cerv1no.ecommerce.mapper;

import com.cerv1no.ecommerce.dto.CommentDto;
import com.cerv1no.ecommerce.dto.ProductDto;
import com.cerv1no.ecommerce.model.Comment;
import com.cerv1no.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target ="imageUrl", source = "imageUrl")
    ProductDto toDto(Product product);

    @Mapping(target ="imageUrl", source = "imageUrl")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "userId", source = "user.id")
    CommentDto toDto(Comment comment);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product", ignore = true)
    Comment toEntity(CommentDto commentDto);

}
