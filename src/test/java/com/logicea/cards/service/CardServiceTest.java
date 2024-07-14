package com.logicea.cards.service;

import com.logicea.cards.domain.entity.Card;
import com.logicea.cards.domain.entity.User;
import com.logicea.cards.domain.enumeration.Status;
import com.logicea.cards.domain.mapper.CardMapperImpl;
import com.logicea.cards.domain.repository.CardRepository;
import com.logicea.cards.dto.CardRequestDto;
import com.logicea.cards.dto.CardResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;
    private CardService cardService;
    private CardMapperImpl cardMapper;

    @BeforeEach
    void setup() {
        cardMapper = new CardMapperImpl();
        cardService = new CardService(cardRepository, cardMapper);
    }

    @DisplayName("To test the add card addition feature")
    @Test
    void testAddCard() {
        Mockito.when(cardRepository.save(any()))
                .thenReturn(
                        Card.builder()
                            .id(1L)
                            .name("Test Card")
                            .description("Test Description")
                            .color("Red")
                            .status(Status.TODO)
                            .build()
                );
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .name("Test Card")
                .description("Test Description")
                .color("Red")
                .status(Status.TODO)
                .build();
        User user = User.builder()
                .id(1L)
                .email("test@mail.com")
                .build();

        CardResponseDto responseDto = cardService.addCard(cardRequestDto, user);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.name()).isEqualTo("Test Card");

        Mockito.verify(cardRepository, Mockito.times(1)).save(any());
    }
}
