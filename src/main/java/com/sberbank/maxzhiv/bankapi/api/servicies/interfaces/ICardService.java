package com.sberbank.maxzhiv.bankapi.api.servicies.interfaces;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardDto;
import com.sberbank.maxzhiv.bankapi.api.dto.CardMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.dto.TransferDto;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ICardService {
    List<CardDto> getCardByAccountId(Integer accountId);
    CardDto createCard(Integer accountId);
    AckDto transfer(TransferDto transferDto);
    CardMoneyDto getMoneyBalance(Integer cardId);
    AckDto deleteCard(Integer accountId);
}
