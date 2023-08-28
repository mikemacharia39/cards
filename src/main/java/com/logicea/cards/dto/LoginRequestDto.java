package com.logicea.cards.dto;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String email,
        String password
) {
}
