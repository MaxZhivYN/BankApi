package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
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
        if (money < 0)
            throw new BadRequestException("money need to be > 0");

        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        cardDAO.pushMoneyToCardAndAccount(money, card);

        return cardDtoFactory.makeCardDto(card);
    }

    @Override
    public CardMoneyDto getMoneyBalance(Integer cardId) {
        CardEntity card = cardDAO.findCardByIdOrThrowException(cardId);

        return cardDtoFactory.makeCardMoneyDto(card);
    }
}
