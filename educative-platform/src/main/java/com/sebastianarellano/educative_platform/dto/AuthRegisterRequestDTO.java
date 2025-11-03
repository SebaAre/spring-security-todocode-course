package com.sebastianarellano.educative_platform.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRegisterRequestDTO(@NotBlank String username,
                                     @NotBlank String password,
                                     @NotBlank String roleRequest) {
}
