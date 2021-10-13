package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.ICardDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

            return query.getSingleResult();
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
    public CardEntity createCard(AccountEntity account, String number) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            CardEntity card = CardEntity.builder()
                    .account(account)
                    .number(number)
                    .build();

            session.beginTransaction();
            session.save(card);
            session.getTransaction().commit();

            return card;
        }
    }

    @Override
    public void deleteCard(CardEntity card) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            session.beginTransaction();
            session.delete(card);

            session.getTransaction().commit();
        }
    }

    @Override
    public SortedSet<String> getCardNumbers() {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<CardEntity> query = session.createQuery("from CardEntity", CardEntity.class);

            SortedSet<String> cardNumbers = query.list().stream()
                    .map(CardEntity::getNumber)
                    .collect(Collectors.toCollection(TreeSet::new));

            cardNumbers.add("10000000000000000");

            return cardNumbers;
        }
    }

    @Override
    public CardEntity findCardByNumberOrThrowException(String number) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<CardEntity> query = session.createQuery("from CardEntity as card where card.number = :number", CardEntity.class);

            query.setParameter("number", number);

            if (query.list().isEmpty())
                throw new NotFoundException(String.format("Card with number %s not found", number));

            return query.getSingleResult();
        }
    }


}
