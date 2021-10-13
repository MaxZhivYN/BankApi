package com.sberbank.maxzhiv.bankapi.api.controllers;


import com.sberbank.maxzhiv.bankapi.api.dto.UserCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.UserDto;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class UserController {
    private final IUserService userService;

    private static final String CREATE_USER = "users";

    @PostMapping(CREATE_USER)
    public UserDto create(
            @RequestBody UserCreateDto userCreateDto) {

        return userService.create(userCreateDto);
    }
}
