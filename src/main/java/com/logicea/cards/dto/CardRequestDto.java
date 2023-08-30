package com.logicea.cards.dto;

import com.logicea.cards.domain.enumeration.Status;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
public record CardRequestDto(
        @NotBlank(message = "Card name is mandatory")
        @Size(max = 50, message = "Card name cannot be longer than 50 characters")
        String name,
        @Size(max = 255, message = "Card description cannot be longer than 255 characters")
        String description,
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color must be a valid hex code")
        @Size(max = 7, message = "Color cannot be longer than 7 characters")
        String color,
        Status status
) {
}
