package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.Data;

@Data
public class PartnerCreateDto {
    private String firstname;

    private String lastname;

    private String email;

    private String bankName;
}
