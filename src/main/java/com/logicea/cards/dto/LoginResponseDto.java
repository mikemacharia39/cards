package com.logicea.cards.dto;

import com.logicea.cards.domain.enumeration.Role;
import lombok.Builder;

@Builder
public record LoginResponseDto(
        String token,
        Long expiresIn
) {
}
