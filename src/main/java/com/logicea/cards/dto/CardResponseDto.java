package com.logicea.cards.dto;

import com.logicea.cards.domain.enumeration.Status;
import lombok.Builder;

@Builder
public record CardResponseDto(
        Long id,
        String name,
        String description,
        String color,
        Status status,
        String dateCreated
) {
}
