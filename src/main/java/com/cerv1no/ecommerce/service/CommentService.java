package com.cerv1no.ecommerce.service;

import com.cerv1no.ecommerce.dto.CommentDto;
import com.cerv1no.ecommerce.exception.ResourceNotFoundException;
import com.cerv1no.ecommerce.mapper.CommentMapper;
import com.cerv1no.ecommerce.model.Comment;
import com.cerv1no.ecommerce.model.Product;
import com.cerv1no.ecommerce.model.User;
import com.cerv1no.ecommerce.repositories.CommentRepository;
import com.cerv1no.ecommerce.repositories.ProductRepository;
import com.cerv1no.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, ProductRepository productRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    public List<CommentDto> getCommentsByProductId(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto createComment(Long userId, Long productId, CommentDto commentDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUser(user);
        comment.setProduct(product);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

}
