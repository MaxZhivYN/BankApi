package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateDto {
    private String username;

    private String firstname;

    private String lastname;

    private String email;
}
