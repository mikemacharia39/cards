package com.logicea.cards.dto;

import com.logicea.cards.domain.enumeration.Status;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record CardRequestDto(

        @NotBlank(message = "Card name is mandatory")
        String name,
        String description,
        String color,
        Status status
) {
}
