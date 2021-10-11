package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.AccountDto;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class AccountController {
    private final IAccountService accountService;

    private static final String CREATE_ACCOUNT = "accounts";


    // TODO: реализовать создание счета с передачей аккаутна
    @PostMapping(CREATE_ACCOUNT)
    public AccountDto create() {
        return null;
    }

}
