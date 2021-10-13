package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IRoleDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDAO implements IRoleDAO {
    private final DBConfiguration dbConfiguration;

    @Override
    public RoleEntity getUserRole() {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<RoleEntity> roleUser = session.createQuery("from RoleEntity as role where role.name = 'USER'", RoleEntity.class);

            return roleUser.getSingleResult();
        }
    }
}
