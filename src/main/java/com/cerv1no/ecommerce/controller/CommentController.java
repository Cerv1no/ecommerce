package com.cerv1no.ecommerce.controller;

import com.cerv1no.ecommerce.dto.CommentDto;
import com.cerv1no.ecommerce.model.User;
import com.cerv1no.ecommerce.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentDto>> getCommentsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(commentService.getCommentsByProductId(productId));
    }

    @PostMapping("/product/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long productId,
                                                    @AuthenticationPrincipal UserDetails userDetails,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(((User)userDetails).getId(), productId, commentDto));
    }

}
