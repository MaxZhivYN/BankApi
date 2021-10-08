package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.ICardDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardDAO implements ICardDAO {
    private final DBConfiguration dbConfiguration;

    @Override
    public CardEntity findCardByIdOrThrowException(Integer cardId) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<CardEntity> query = session.createQuery("from CardEntity as card where card.id = :cardId", CardEntity.class);

            query.setParameter("cardId", cardId);

            if (query.list().isEmpty())
                throw new NotFoundException(String.format("Card with id %s not found", cardId));

            return session.get(CardEntity.class, cardId);
        }
    }

    @Override
    public List<CardEntity> getAllCardsByAccountId(Integer accountId) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<CardEntity> query = session.createQuery("from CardEntity as card where card.account.id = :accountId", CardEntity.class);

            query.setParameter("accountId", accountId);

            return query.list();
        }
    }

    @Override
    public void pushMoneyToCardAndAccount(Double money, CardEntity card) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            card.setBalance(card.getBalance() + money);

            session.beginTransaction();
            session.update(card);

            AccountEntity account = card.getAccount();
            account.setBalance(account.getBalance() + money);
            session.update(account);

            session.getTransaction().commit();
        }
    }

    @Override
    public CardEntity createCard(AccountEntity account, String name) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<CardEntity> query = session.createQuery("from CardEntity as card where card.name = :cardName and card.account.id = :accountId", CardEntity.class);
            query.setParameter("cardName", name);
            query.setParameter("accountId", account.getId());

            if (!query.list().isEmpty()) {
                throw new BadRequestException(String.format("name \"%s\" already used in this account", name));
            }

            CardEntity card = CardEntity.builder()
                    .account(account)
                    .name(name)
                    .balance(0D)
                    .build();

            session.beginTransaction();
            session.save(card);
            session.getTransaction().commit();

            return card;
        }
    }


}
