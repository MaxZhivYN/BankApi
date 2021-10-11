package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;

import java.util.List;
import java.util.SortedSet;

public interface ICardDAO {
    CardEntity findCardByIdOrThrowException(Integer cardId);
    List<CardEntity> getAllCardsByAccountId(Integer accountId);
    void pushMoney(Double money, CardEntity card);
    CardEntity createCard(AccountEntity account, String number);
    void deleteCard(CardEntity card);
    SortedSet<String> getCardNumbers();
}
