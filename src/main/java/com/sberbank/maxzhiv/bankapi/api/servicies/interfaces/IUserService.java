package com.sberbank.maxzhiv.bankapi.api.servicies.interfaces;

import com.sberbank.maxzhiv.bankapi.api.dto.UserCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.UserDto;

public interface IUserService {
    UserDto create(UserCreateDto userCreateDto);
}
