package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.factories.CardDtoFactory;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@RestController
public class CardController {
    private final ICardService cardService;

    private static final String GET_CARD_BY_ACCOUNT_ID = "api/accounts/{account_id}/cards";
    private static final String CREATE_CARD = "api/accounts/{account_id}/cards";
    private static final String PUSH_MONEY_TO_CARD = "api/cards/{card_id}";
    private static final String GET_MONEY_BALANCE = "api/cards/{card_id}";

    @GetMapping(GET_CARD_BY_ACCOUNT_ID)
    public List<CardDto> getCardByAccountId(
            @PathVariable("account_id") Integer accountId) {

        return cardService.getCardByAccountId(accountId);
    }

    @PostMapping(CREATE_CARD)
    public CardDto createCard(
            @PathVariable("account_id") Integer accountId,
            @RequestParam(name = "name") String cardName) {

        return cardService.createCard(accountId, cardName);
    }

    @PatchMapping(PUSH_MONEY_TO_CARD)
    public CardDto pushMoneyToCard(
            @PathVariable("card_id") Integer cardId,
            @RequestParam(name = "money") Double money) {

        return cardService.pushMoneyToCard(cardId, money);
    }

    @GetMapping(GET_MONEY_BALANCE)
    public CardMoneyDto getMoneyBalance(
            @PathVariable("card_id") Integer cardId) {

       return cardService.getMoneyBalance(cardId);
    }
}
