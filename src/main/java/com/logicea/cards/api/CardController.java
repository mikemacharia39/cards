package com.logicea.cards.api;

import com.logicea.cards.domain.entity.User;
import com.logicea.cards.domain.enumeration.Status;
import com.logicea.cards.dto.CardRequestDto;
import com.logicea.cards.dto.CardResponseDto;
import com.logicea.cards.service.CardService;

import com.logicea.cards.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @ApiOperation(value = "Create a card")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Card created", response = CardResponseDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(consumes = "application/json", produces = {"application/json", "application/problem+json"})
    public ResponseEntity<CardResponseDto> createCard(@Valid @RequestBody CardRequestDto request, HttpServletRequest servletRequest) {
        final Principal principal = servletRequest.getUserPrincipal();
        final User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(cardService.addCard(request, user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDto> updateCard(@Valid @PathVariable Long id, @Valid @RequestBody CardRequestDto request,
                                                      HttpServletRequest servletRequest) {
        final Principal principal = servletRequest.getUserPrincipal();
        final User user = userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(cardService.updateCard(id, request, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCard(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCard(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CardResponseDto>> searchCards(@Valid @RequestParam(value = "statuses", required = false) List<Status> statuses,
                                                             @Valid @RequestParam(value = "dateCreated", required = false) LocalDate dateCreated,
                                                             @Valid @RequestParam(value = "search", required = false) String search,
                                                             final Pageable pageable,
                                                             final HttpServletRequest servletRequest) {
        final Principal principal = servletRequest.getUserPrincipal();
        final User user = userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(cardService.searchCards(statuses, dateCreated, search, pageable, user));
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@Valid @PathVariable Long id) {
        cardService.deleteCard(id);
        ResponseEntity.noContent().build();
    }
}
