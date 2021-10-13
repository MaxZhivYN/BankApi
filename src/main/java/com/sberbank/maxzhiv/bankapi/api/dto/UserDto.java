package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@Builder
public class UserDto {
    private Integer id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;
}
