package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;

public interface IAccountDAO {
    AccountEntity getAccountByIdOrThrowException(Integer accountId);
}
