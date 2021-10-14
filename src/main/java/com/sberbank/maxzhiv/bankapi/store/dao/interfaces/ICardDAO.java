package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;

import java.util.List;
import java.util.SortedSet;

public interface ICardDAO {
    CardEntity findCardByIdOrThrowException(Integer cardId);
    CardEntity getCardByAccountIdOrThrowException(Integer accountId);
    CardEntity createCard(AccountEntity account, String number);
    void deleteCard(CardEntity card);
    SortedSet<String> getCardNumbers();
    CardEntity findCardByNumberOrThrowException(String number);

}
