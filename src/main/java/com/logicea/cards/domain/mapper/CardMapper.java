package com.logicea.cards.domain.mapper;

import com.logicea.cards.domain.entity.Card;
import com.logicea.cards.domain.entity.User;
import com.logicea.cards.dto.CardRequestDto;
import com.logicea.cards.dto.CardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Card toEntity(CardRequestDto dto, User user);

    @Mapping(target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "status", source = "status")
    CardResponseDto toDto(Card entity);
}
