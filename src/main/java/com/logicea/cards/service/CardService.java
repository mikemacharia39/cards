package com.logicea.cards.service;

import com.logicea.cards.domain.entity.Card;
import com.logicea.cards.domain.entity.User;
import com.logicea.cards.domain.enumeration.Status;
import com.logicea.cards.domain.mapper.CardMapper;
import com.logicea.cards.domain.repository.CardRepository;
import com.logicea.cards.domain.specification.CardSpecification;
import com.logicea.cards.dto.CardRequestDto;
import com.logicea.cards.dto.CardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardResponseDto addCard(final CardRequestDto dto, final User user) {
        Card card = cardRepository.save(cardMapper.toEntity(dto, user));
        log.info("Card created: {}", card.getName());
        return cardMapper.toDto(card);
    }

    public CardResponseDto updateCard(final Long id, final CardRequestDto dto, final User user) {
        Card card = cardRepository.findById(id)
                .map(card1 -> cardMapper.toEntity(dto, user))
                .orElseThrow();
        return cardMapper.toDto(cardRepository.save(card));
    }

    public CardResponseDto getCard(final Long id) {
        return cardRepository.findById(id)
                .map(cardMapper::toDto)
                .orElseThrow();
    }

    public Page<CardResponseDto> searchCards(final List<Status> status, final LocalDate dateCreated, final String search, final Pageable pageable) {
        return cardRepository.findAll(CardSpecification.searchCard(status, dateCreated, search), pageable)
                .map(cardMapper::toDto);
    }

    public void deleteCard(final Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
        } else {
            throw new RuntimeException("Card not found");
        }
    }
}
