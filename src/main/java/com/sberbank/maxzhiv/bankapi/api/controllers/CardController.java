package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class CardController {
    private final ICardService cardService;

    private static final String GET_CARDS_BY_ACCOUNT_ID = "accounts/{account_id}/cards";
    private static final String CREATE_CARD = "accounts/{account_id}/cards";
    private static final String PUSH_MONEY_TO_CARD = "cards/{card_id}";
    private static final String GET_MONEY_BALANCE = "cards/{card_id}";
    private static final String DELETE_CARD = "accounts/{account_id}/cards/{card_id}";

    @GetMapping(GET_CARDS_BY_ACCOUNT_ID)
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

    @DeleteMapping(DELETE_CARD)
    public AckDto deleteCard(
            @PathVariable("account_id") Integer accountId,
            @PathVariable("card_id") Integer cardId) {

        return cardService.deleteCard(accountId, cardId);
    }



}
