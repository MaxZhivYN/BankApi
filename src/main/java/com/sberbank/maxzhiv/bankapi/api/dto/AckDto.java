package com.sberbank.maxzhiv.bankapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AckDto {
    private Boolean answer;

    private String description;

    public static AckDto makeDefault(Boolean answer, String description) {
        return AckDto.builder()
                .answer(answer)
                .description(description)
                .build();
    }
}