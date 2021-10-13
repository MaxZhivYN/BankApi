package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IBankDAO;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IPartnerDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.BankEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.PartnerEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class PartnerDAO implements IPartnerDAO {
    private final DBConfiguration dbConfiguration;
    private final IBankDAO bankDAO;

    @Override
    public PartnerEntity create(String firstname, String lastname, String email, String bankName) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            BankEntity bank = bankDAO.findByNameOrThrowException(bankName);

            PartnerEntity partner = PartnerEntity.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .bank(bank)
                    .build();

            session.beginTransaction();
            session.save(partner);
            session.getTransaction().commit();

            return partner;
        }
    }

    @Override
    public List<PartnerEntity> getAll() {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<PartnerEntity> partners = session.createQuery("from PartnerEntity", PartnerEntity.class);

            return partners.list();
        }
    }

    @Override
    public PartnerEntity findByIdOrThrowException(Integer id) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<PartnerEntity> query = session.createQuery("from PartnerEntity as partner where partner.id = :id", PartnerEntity.class);

            query.setParameter("id", id);

            if (query.list().isEmpty())
                throw new NotFoundException(String.format("Partner with id %s not found", id));

            return query.getSingleResult();
        }
    }
}
