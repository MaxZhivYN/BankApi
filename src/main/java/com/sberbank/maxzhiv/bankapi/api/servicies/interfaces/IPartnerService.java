package com.sberbank.maxzhiv.bankapi.api.servicies.interfaces;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerGetMoneyDto;

import java.util.List;

public interface IPartnerService {
    PartnerDto create(PartnerCreateDto partnerCreateDto);
    List<PartnerDto> getAll();
    AckDto pushMoneyToPartner(Integer partnerId, PartnerGetMoneyDto partnerGetMoneyDto);
}
