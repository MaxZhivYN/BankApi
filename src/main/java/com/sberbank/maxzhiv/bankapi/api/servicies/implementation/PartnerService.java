package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.AckDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerCreateDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerDto;
import com.sberbank.maxzhiv.bankapi.api.dto.PartnerGetMoneyDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NoMoneyException;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IPartnerService;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IAccountDAO;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IBankDAO;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.ICardDAO;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IPartnerDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.PartnerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PartnerService implements IPartnerService {
    private final IBankDAO bankDAO;
    private final IPartnerDAO partnerDAO;
    private final ICardDAO cardDAO;
    private final IAccountDAO accountDAO;

    @Override
    public PartnerDto create(PartnerCreateDto partnerCreateDto) {
        String firstname = partnerCreateDto.getFirstname();
        String lastname = partnerCreateDto.getLastname();
        String email = partnerCreateDto.getEmail();
        String bankName = partnerCreateDto.getBankName();

        if (Objects.isNull(firstname)) {
            throw new BadRequestException("'firstname' must be not null");
        }

        if (Objects.isNull(lastname)) {
            throw new BadRequestException("'lastname' must be not null");
        }

        if (Objects.isNull(email)) {
            throw new BadRequestException("'email' must be not null");
        }

        if (Objects.isNull(bankName)) {
            throw new BadRequestException("'bankName' must be not null");
        }

        if (!bankDAO.isValidBankName(bankName)) {
            throw new BadRequestException(String.format("we don't work with bank '%s'", bankName));
        }

        PartnerEntity partner = partnerDAO.create(firstname, lastname, email, bankName);

        return makePartnerDto(partner);
    }

    @Override
    public List<PartnerDto> getAll() {
        List<PartnerEntity> partners = partnerDAO.getAll();

        return partners.stream()
                .map(this::makePartnerDto)
                .collect(Collectors.toList());
    }

    @Override
    public AckDto pushMoneyToPartner(Integer partnerId, PartnerGetMoneyDto partnerGetMoneyDto) {
        String cardFromNumber = partnerGetMoneyDto.getCardFromNumber();
        Double money = partnerGetMoneyDto.getMoney();

        if (Objects.isNull(cardFromNumber)) {
            throw new BadRequestException("'cardFromNumber' must be not null");
        }

        if (Objects.isNull(money)) {
            throw new BadRequestException("'money' must be not null");
        }

        if (money < 0) {
            throw new BadRequestException("'money' nned to ne > 0");
        }

        partnerDAO.findByIdOrThrowException(partnerId);

        CardEntity cardFrom = cardDAO.findCardByNumberOrThrowException(cardFromNumber);

        double cardBalance = cardFrom.getAccount().getBalance();

        if (cardBalance - money < 0) {
            throw new NoMoneyException(String.format("No money enough on card '%s'", cardFromNumber));
        }

        accountDAO.pushMoney(-money, cardFrom.getAccount());

        return null;
    }

    private PartnerDto makePartnerDto(PartnerEntity partnerEntity) {
        return PartnerDto.builder()
                .id(partnerEntity.getId())
                .firstname(partnerEntity.getFirstname())
                .lastname(partnerEntity.getLastname())
                .email(partnerEntity.getEmail())
                .bankName(partnerEntity.getBank().getName())
                .build();
    }
}
