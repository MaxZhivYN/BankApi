package com.sberbank.maxzhiv.bankapi.api.servicies.interfaces;

import com.sberbank.maxzhiv.bankapi.api.dto.AccountCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.AccountDto;

public interface IAccountService {
    AccountDto create(Integer userId);
}
