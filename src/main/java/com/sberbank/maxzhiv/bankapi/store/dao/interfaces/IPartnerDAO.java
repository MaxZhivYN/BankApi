package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.PartnerEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IPartnerDAO {
    PartnerEntity create(String firstname, String lastname, String email, String bankName);
    List<PartnerEntity> getAll();
    PartnerEntity findByIdOrThrowException(Integer id);
}
