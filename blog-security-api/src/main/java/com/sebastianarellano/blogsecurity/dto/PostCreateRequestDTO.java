package com.sebastianarellano.blogsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequestDTO(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must have between 3 and 200 characters")
        String title,

        @NotBlank(message = "Content is required")
        @Size(min = 10, message = "Content must be at least 10 characters")
        String content,

        @NotNull(message = "Author ID is required")
        Long authorId

) {}
