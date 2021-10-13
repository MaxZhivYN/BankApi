package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IAccountDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountDAO implements IAccountDAO {
    private final DBConfiguration dbConfiguration;

    @Override
    public AccountEntity getAccountByIdOrThrowException(Integer accountId) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<AccountEntity> query = session.createQuery("from AccountEntity as acc where acc.id = :accountId", AccountEntity.class);

            query.setParameter("accountId", accountId);

            if (query.list().isEmpty())
                throw new NotFoundException(String.format("Account with id %s not found", accountId));

            return query.getSingleResult();
        }
    }

    @Override
    public Double getBalance(Integer accountId) {
        AccountEntity account = getAccountByIdOrThrowException(accountId);

        return account.getBalance();
    }

    @Override
    public void pushMoney(Double money, AccountEntity account) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            account.setBalance(account.getBalance() + money);

            session.beginTransaction();
            session.update(account);

            session.getTransaction().commit();
        } catch (Exception e) {
            dbConfiguration.getFactory().getCurrentSession()
                    .getTransaction().rollback();
        }
    }

    @Override
    public AccountEntity create(UserEntity user) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            AccountEntity account = AccountEntity.builder()
                    .user(user)
                    .balance(0D)
                    .build();

            session.beginTransaction();
            session.save(account);
            session.getTransaction().commit();

            return account;
        }
    }


}
