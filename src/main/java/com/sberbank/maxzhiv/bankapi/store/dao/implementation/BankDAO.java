package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IBankDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.BankEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BankDAO implements IBankDAO {
    private final DBConfiguration dbConfiguration;


    @Override
    public boolean isValidBankName(String bankName) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<BankEntity> banks = session.createQuery("from BankEntity", BankEntity.class);

            return banks.list().stream()
                    .anyMatch(bank -> bank.getName().equals(bankName));
        }
    }

    @Override
    public BankEntity findByNameOrThrowException(String name) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<BankEntity> query = session.createQuery("from BankEntity as bank where bank.name = :name", BankEntity.class);

            query.setParameter("name", name);

            if (query.list().isEmpty())
                throw new NotFoundException(String.format("Bank with name %s not found", name));

            return query.getSingleResult();
        }
    }
}
