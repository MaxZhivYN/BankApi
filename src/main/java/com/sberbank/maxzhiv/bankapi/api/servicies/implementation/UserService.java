package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.UserCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.UserDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IUserService;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IUserDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDAO userDAO;

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        if (Objects.isNull(userCreateDto.getUsername())) {
            throw new BadRequestException("'username' must be not null");
        }

        if (Objects.isNull(userCreateDto.getFirstname())) {
            throw new BadRequestException("'firstname' must be not null");
        }

        if (Objects.isNull(userCreateDto.getLastname())) {
            throw new BadRequestException("'lastname' must be not null");
        }

        if (Objects.isNull(userCreateDto.getEmail())) {
            throw new BadRequestException("'email' must be not null");
        }

        UserEntity user = userDAO.create(userCreateDto);

        return makeUserDto(user);
    }

    private UserDto makeUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .build();
    }
}
