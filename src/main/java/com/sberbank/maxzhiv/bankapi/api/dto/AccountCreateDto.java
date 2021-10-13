package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class AccountCreateDto {
    Integer userId;
}
