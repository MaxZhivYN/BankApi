package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.api.dto.UserCreateDto;
import com.sberbank.maxzhiv.bankapi.store.entities.UserEntity;

public interface IUserDAO {
    UserEntity getUserByIdOrThrowException(Integer userId);
    UserEntity create(UserCreateDto userCreateDto);
}
