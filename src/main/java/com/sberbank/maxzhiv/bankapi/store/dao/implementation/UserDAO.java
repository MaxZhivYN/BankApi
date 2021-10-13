package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.UserCreateDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IUserDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.AccountEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.RoleEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserDAO implements IUserDAO {
    private final DBConfiguration dbConfiguration;
    private final RoleDAO roleDAO;

    @Override
    public UserEntity getUserByIdOrThrowException(Integer userId) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<UserEntity> query = session.createQuery("from UserEntity as user where user.id = :accountId", UserEntity.class);

            query.setParameter("accountId", userId);

            if (query.list().isEmpty())
                throw new NotFoundException(String.format("User with id %s not found", userId));

            return query.getSingleResult();
        }
    }

    @Override
    public UserEntity create(UserCreateDto userCreateDto) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<UserEntity> userWithCurrentUsername = session.createQuery("from UserEntity as user where user.username = :currentUsername", UserEntity.class);
            userWithCurrentUsername.setParameter("currentUsername", userCreateDto.getUsername());

            if (!userWithCurrentUsername.list().isEmpty()) {
                throw new BadRequestException("This user already exists");
            }

            List<RoleEntity> roles = new ArrayList<>();
            RoleEntity roleUser = roleDAO.getUserRole();
            roles.add(roleUser);

            UserEntity user = UserEntity.builder()
                    .username(userCreateDto.getUsername())
                    .firstname(userCreateDto.getFirstname())
                    .lastname(userCreateDto.getLastname())
                    .email(userCreateDto.getEmail())
                    .roles(roles)
                    .build();

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            return user;
        }
    }
}
