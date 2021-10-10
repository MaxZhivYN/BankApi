package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;

import java.util.List;

public interface ICardDAO {
    CardEntity findCardByIdOrThrowException(Integer cardId);
    List<CardEntity> getAllCardsByAccountId(Integer accountId);
    void pushMoney(Double money, CardEntity card);
    CardEntity createCard(AccountEntity account, String name);
    void deleteCard(CardEntity card);
}
