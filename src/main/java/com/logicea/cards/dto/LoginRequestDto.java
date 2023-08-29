package com.logicea.cards.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record LoginRequestDto(
        @NotBlank
        @Size(max = 50)
        String email,
        @NotBlank
        @Size(max = 50)
        String password
) {
}
