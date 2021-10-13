package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.AccountCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.AccountDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IAccountService;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IAccountDAO;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IUserDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {
    private final IUserDAO userDAO;
    private final IAccountDAO accountDAO;

    @Override
    public AccountDto create(Integer userId) {

        UserEntity user = userDAO.getUserByIdOrThrowException(userId);

        AccountEntity account = accountDAO.create(user);

        return makeAccountDto(account);
    }

    AccountDto makeAccountDto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .id(accountEntity.getId())
                .balance(accountEntity.getBalance())
                .build();
    }
}
