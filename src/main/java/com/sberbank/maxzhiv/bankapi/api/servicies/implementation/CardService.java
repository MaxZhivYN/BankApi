package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.factories.CardDtoFactory;
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
    private final CardDtoFactory cardDtoFactory;
    private final ICardDAO cardDAO;
    private final IAccountDAO accountDAO;

    @Override
    public List<CardDto> getCardByAccountId(Integer accountId) {
        if (accountId < 0)
            throw new BadRequestException("account_id must be > 0");

        accountDAO.getAccountByIdOrThrowException(accountId);

        List<CardEntity> cardEntities = cardDAO.getAllCardsByAccountId(accountId);

        return cardEntities.stream()
                .map(cardDtoFactory::makeCardDto)
                .collect(Collectors.toList());
    }

    @Override
    public CardDto createCard(Integer accountId, String cardName) {
        AccountEntity account = accountDAO.getAccountByIdOrThrowException(accountId);

        CardEntity card = cardDAO.createCard(account, cardName);

        return cardDtoFactory.makeCardDto(card);
    }

    @Override
    public CardDto pushMoneyToCard(Integer cardId, Double money) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        if (card.getBalance() + money < 0) {
            throw new BadRequestException("Insufficient funds on card");
        }

        cardDAO.pushMoney(money, card);
        accountDAO.pushMoney(money, card.getAccount());

        return cardDtoFactory.makeCardDto(card);
    }

    @Override
    public CardMoneyDto getMoneyBalance(Integer cardId) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        return cardDtoFactory.makeCardMoneyDto(card);
    }

    @Override
    public AckDto deleteCard(Integer accountId, Integer cardId) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        if (!card.getAccount().getId().equals(accountId))
            throw new BadRequestException("No card this card on account");

        cardDAO.deleteCard(card);

        return AckDto.makeDefault(true);
    }
}
