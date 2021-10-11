package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.ICardService;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IAccountDAO;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.ICardDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardService implements ICardService {
    private final ICardDAO cardDAO;
    private final IAccountDAO accountDAO;

    @Override
    public List<CardDto> getCardByAccountId(Integer accountId) {
        if (accountId < 0)
            throw new BadRequestException("account_id must be > 0");

        accountDAO.getAccountByIdOrThrowException(accountId);

        List<CardEntity> cardEntities = cardDAO.getAllCardsByAccountId(accountId);

        return cardEntities.stream()
                .map(this::makeCardDto)
                .collect(Collectors.toList());
    }

    @Override
    public CardDto createCard(Integer accountId) {
        AccountEntity account = accountDAO.getAccountByIdOrThrowException(accountId);

        CardEntity card = cardDAO.createCard(account, cardName);

        return makeCardDto(card);
    }

    @Override
    public CardDto pushMoneyToCard(Integer cardId, Double money) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

//        if (card.getBalance() + money < 0) {
//            throw new BadRequestException("Insufficient funds on card");
//        }

        cardDAO.pushMoney(money, card);
        accountDAO.pushMoney(money, card.getAccount());

        return makeCardDto(card);
    }

    @Override
    public CardMoneyDto getMoneyBalance(Integer cardId) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        return makeCardMoneyDto(card.getAccount());
    }

    @Override
    public AckDto deleteCard(Integer accountId, Integer cardId) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        if (!card.getAccount().getId().equals(accountId))
            throw new BadRequestException("No card this card on account");

        cardDAO.deleteCard(card);

        return AckDto.makeDefault(true);
    }

    private CardDto makeCardDto(CardEntity cardEntity) {
        return CardDto.builder()
                .id(cardEntity.getId())
                .number(cardEntity.getNumber())
                .build();
    }

    private CardMoneyDto makeCardMoneyDto(AccountEntity accountEntity) {
        return CardMoneyDto.builder()
                .money(accountEntity.getBalance())
                .build();
    }
}
