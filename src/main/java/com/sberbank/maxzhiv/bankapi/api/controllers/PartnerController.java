package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerGetMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IPartnerService;
import com.sberbank.maxzhiv.bankapi.store.entities.PartnerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PartnerController {
    private final IPartnerService partnerService;

    private static final String CREATE_PARTNER = "partners";
    private static final String GET_PARTNERS = "partners";
    private static final String PUSH_MONEY_TO_PARTNER = "partner/{partner_id}";

    @PostMapping(CREATE_PARTNER)
    public PartnerDto create(
            @RequestBody PartnerCreateDto partnerCreateDto) {

        return partnerService.create(partnerCreateDto);
    }

    @GetMapping(GET_PARTNERS)
    public List<PartnerDto> getAll() {
        return partnerService.getAll();
    }

    @PatchMapping(PUSH_MONEY_TO_PARTNER)
    public AckDto pushMoneyToPartner(
            @PathVariable("partner_id") Integer partnerId,
            @RequestBody PartnerGetMoneyDto partnerGetMoneyDto) {

        return partnerService.pushMoneyToPartner(partnerId, partnerGetMoneyDto);
    }
}