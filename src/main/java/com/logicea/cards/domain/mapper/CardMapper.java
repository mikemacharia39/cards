package com.logicea.cards.domain.mapper;

import com.logicea.cards.domain.entity.Card;
import com.logicea.cards.dto.CardRequestDto;
import com.logicea.cards.dto.CardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Card toEntity(CardRequestDto dto);

    CardResponseDto toDto(Card entity);
}
