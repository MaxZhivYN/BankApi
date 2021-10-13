package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.AccountCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.AccountDto;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class AccountController {
    private final IAccountService accountService;

    private static final String CREATE_ACCOUNT = "accounts";


    @PostMapping(CREATE_ACCOUNT)
    public AccountDto create(
            @RequestParam("userId") Integer userId) {
        return accountService.create(userId);
    }

}
