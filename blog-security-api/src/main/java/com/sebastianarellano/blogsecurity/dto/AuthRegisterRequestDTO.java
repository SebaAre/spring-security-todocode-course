package com.sebastianarellano.blogsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record AuthRegisterRequestDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50)
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6)
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email

){}