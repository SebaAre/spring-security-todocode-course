package com.sebastianarellano.blogsecurity.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "title", "content", "authorId", "authorName", "createdAt", "updatedAt"})
public record PostResponseDTO(
        Long id,
        String title,
        String content,
        Long authorId,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}