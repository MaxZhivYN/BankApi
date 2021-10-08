package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.factories.CardDtoFactory;
import com.sberbank.maxzhiv.bankapi.api.servicies.AccountService;
import com.sberbank.maxzhiv.bankapi.api.servicies.CardService;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@RestController
public class CardController {
    private final CardService cardService;
    private final AccountService accountService;
    private final CardDtoFactory cardDtoFactory;

    private static final String GET_CARD_BY_ACCOUNT_ID = "api/accounts/{account_id}/cards";
    private static final String CREATE_CARD = "api/accounts/{account_id}/cards";
    private static final String PUSH_MONEY_TO_CARD = "api/cards/{card_id}";
    private static final String GET_MONEY_BALANCE = "api/cards/{card_id}";

    @GetMapping(GET_CARD_BY_ACCOUNT_ID)
    public List<CardDto> getCardByAccountId(
            @PathVariable("account_id") Integer accountId) {

        if (accountId < 0)
            throw new BadRequestException("account_id must be > 0");

        accountService.getAccountByIdOrThrowException(accountId);

        List<CardEntity> cardEntities = cardService.getAllCardsByAccountId(accountId);

        return cardEntities.stream()
                .map(cardDtoFactory::makeCardDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_CARD)
    public CardDto createCard(
            @PathVariable("account_id") Integer accountId,
            @RequestParam(name = "name") String cardName) {

        CardEntity card = cardService.createNewCardByAccount(accountId, cardName);

        return cardDtoFactory.makeCardDto(card);
    }

    @PatchMapping(PUSH_MONEY_TO_CARD)
    public CardDto pushMoneyToCard(
            @PathVariable("card_id") Integer cardId,
            @RequestParam(name = "money") Double money) {

        if (money < 0)
            throw new BadRequestException("money need to be > 0");

        CardEntity card = cardService.pushMoneyToCard(cardId, money);

        return cardDtoFactory.makeCardDto(card);
    }

    @GetMapping(GET_MONEY_BALANCE)
    public CardMoneyDto getMoneyBalance(
            @PathVariable("card_id") Integer cardId) {

        CardEntity card = cardService.findCardByIdOrThrowException(cardId);

        return cardDtoFactory.makeCardMoneyDto(card);
    }
}
