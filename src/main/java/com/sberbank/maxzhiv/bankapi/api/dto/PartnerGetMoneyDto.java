package com.sberbank.maxzhiv.bankapi.api.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartnerGetMoneyDto {
    private String cardFromNumber;

    private Double money;
}
