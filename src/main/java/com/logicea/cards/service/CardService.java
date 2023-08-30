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
import java.util.Objects;
import java.util.Optional;

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
        Card card = findCardById(id, user);

        card.setColor(dto.color());
        card.setDescription(dto.description());
        card.setName(dto.name());
        card.setStatus(dto.status());

        log.info("Card updated: {}", card.getName());
        return cardMapper.toDto(cardRepository.save(card));
    }

    public CardResponseDto getCard(final Long id, final User user) {
        return cardMapper.toDto(findCardById(id, user));
    }

    public Card findCardById(final Long id, final User user) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            throw new RuntimeException("Card not found");
        }
        Card card = optionalCard.get();

        isAuthorized(card, user);

        return card;
    }

    public Page<CardResponseDto> searchCards(final List<Status> status, final LocalDate dateCreated,
                                             final String search, final Pageable pageable, final User user) {
        return cardRepository.findAll(CardSpecification.searchCard(status, dateCreated, search, user), pageable)
                .map(cardMapper::toDto);
    }

    public void deleteCard(final Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
        } else {
            throw new RuntimeException("Card not found");
        }
    }

    private void isAuthorized(final Card card, final User user) {
        if (!Objects.equals(card.getUser().getId(), user.getId()) && !user.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }
    }
}
