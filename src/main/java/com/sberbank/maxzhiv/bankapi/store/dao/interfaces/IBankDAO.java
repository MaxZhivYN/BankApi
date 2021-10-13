package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.BankEntity;

public interface IBankDAO {
    boolean isValidBankName(String bankName);
    BankEntity findByNameOrThrowException(String name);
}
