package com.logicea.cards.api;

import com.logicea.cards.domain.enumeration.Status;
import com.logicea.cards.dto.CardRequestDto;
import com.logicea.cards.dto.CardResponseDto;
import com.logicea.cards.service.CardService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    public final CardService cardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = {"application/json", "application/problem+json"})
    @io.swagger.v3.oas.annotations.Operation(summary = "Create a card")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Card created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Card to create",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CardRequestDto.class)))
    public CardResponseDto createCard(@Valid @RequestBody CardRequestDto request) {
        return cardService.addCard(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CardResponseDto updateCard(@Valid @PathVariable Long id, @Valid @RequestBody CardRequestDto request) {
        return cardService.updateCard(id, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CardResponseDto getCard(@Valid @PathVariable Long id) {
        return cardService.getCard(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Page<CardResponseDto> searchCards(@Valid @RequestParam(value = "statuses", required = false) List<Status> statuses,
                                                             @Valid @RequestParam(value = "dateCreated", required = false) LocalDate dateCreated,
                                                             @Valid @RequestParam(value = "search", required = false) String search,
                                                             final Pageable pageable) {
        return cardService.searchCards(statuses, dateCreated, search, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCard(@Valid @PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
