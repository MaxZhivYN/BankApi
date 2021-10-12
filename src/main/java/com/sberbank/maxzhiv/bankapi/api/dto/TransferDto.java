package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class TransferDto {
    private String fromCardNumber;

    private String toCardNumber;

    private Double money;
}
