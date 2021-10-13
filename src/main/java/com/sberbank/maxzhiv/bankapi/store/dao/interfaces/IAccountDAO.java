package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.UserEntity;

public interface IAccountDAO {
    AccountEntity getAccountByIdOrThrowException(Integer accountId);
    Double getBalance(Integer accountId);
    void pushMoney(Double money, AccountEntity account);
    AccountEntity create(UserEntity user);
}
