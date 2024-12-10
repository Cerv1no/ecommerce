package com.cerv1no.ecommerce.mapper;

import com.cerv1no.ecommerce.dto.CommentDto;
import com.cerv1no.ecommerce.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "userId", source = "user.id")
    CommentDto toDto(Comment comment);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product", ignore = true)
    Comment toEntity(CommentDto commentDto);
}
