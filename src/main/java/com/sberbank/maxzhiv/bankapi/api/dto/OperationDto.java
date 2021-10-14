package com.sberbank.maxzhiv.bankapi.api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationDto {
    private Integer id;

    private String operationType;

    private String operationStatus;

    private String cardNumber;
}
