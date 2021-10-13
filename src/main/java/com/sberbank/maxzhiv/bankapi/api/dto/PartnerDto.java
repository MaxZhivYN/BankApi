package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartnerDto {
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String bankName;
}
