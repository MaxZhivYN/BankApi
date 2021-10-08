package com.sberbank.maxzhiv.bankapi.api.factories;


import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import org.springframework.stereotype.Component;

@Component
public class CardDtoFactory {
    public CardDto makeCardDto(CardEntity cardEntity) {
        return CardDto.builder()
                .id(cardEntity.getId())
                .name(cardEntity.getName())
                .cardBalance(cardEntity.getBalance())
                .build();
    }

    public CardMoneyDto makeCardMoneyDto(CardEntity cardEntity) {
        return CardMoneyDto.builder()
                .money(cardEntity.getBalance())
                .build();
    }
}
