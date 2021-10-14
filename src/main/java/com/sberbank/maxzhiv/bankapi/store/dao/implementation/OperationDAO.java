package com.sberbank.maxzhiv.bankapi.store.dao.implementation;

import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.store.dao.DBConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IOperationDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OperationDAO implements IOperationDAO {
    private final DBConfiguration dbConfiguration;

    @Override
    public OperationStatusEntity getOperationStatusOrThrowException(String name) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<OperationStatusEntity> operationStatus = session.createQuery("from OperationStatusEntity as op_status where op_status.name = :name", OperationStatusEntity.class);
            operationStatus.setParameter("name", name);

            if (operationStatus.list().isEmpty())
                throw new NotFoundException(String.format("OperationStatus with name '%s' not found", name));

            return operationStatus.getSingleResult();
        }
    }

    @Override
    public OperationTypeEntity getOperationTypeOrThrowException(String name) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<OperationTypeEntity> operationType = session.createQuery("from OperationTypeEntity as op_status where op_status.name = :name", OperationTypeEntity.class);
            operationType.setParameter("name", name);

            if (operationType.list().isEmpty())
                throw new NotFoundException(String.format("OperationType with name '%s' not found", name));

            return operationType.getSingleResult();
        }
    }

    @Override
    public OperationEntity createOperation(OperationStatusEntity operationStatus, OperationTypeEntity operationType, CardEntity card) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            OperationEntity operation = OperationEntity.builder()
                    .operationType(operationType)
                    .operationStatus(operationStatus)
                    .card(card)
                    .build();

            session.beginTransaction();
            session.save(operation);
            session.getTransaction().commit();

            return operation;
        }
    }

    @Override
    public List<OperationEntity> getAll() {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<OperationEntity> operations = session.createQuery("from OperationEntity", OperationEntity.class);

            return operations.list();
        }
    }

    @Override
    public List<OperationEntity> getByStatus(String status) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<OperationEntity> operations = session.createQuery("from OperationEntity as oper where oper.operationStatus.name = :status", OperationEntity.class);
            operations.setParameter("status", status);

            return operations.list();
        }
    }

    @Override
    public OperationEntity findOperationByIdOrThrowException(Integer id) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<OperationEntity> operation = session.createQuery("from OperationEntity as oper where oper.id = :id", OperationEntity.class);
            operation.setParameter("id", id);

            if (operation.list().isEmpty())
                throw new NotFoundException(String.format("Operation with id %s not found", id));

            return operation.getSingleResult();
        }
    }

    @Override
    public Boolean isValidStatus(String status) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            Query<OperationStatusEntity> operation = session.createQuery("from OperationStatusEntity as status where status.name = :status", OperationStatusEntity.class);
            operation.setParameter("status", status);

            return !operation.list().isEmpty();
        }
    }

    @Override
    public OperationEntity changeStatus(OperationEntity operation, String newStatus) {
        try (final Session session = dbConfiguration.getFactory().openSession()) {
            OperationStatusEntity newOperationStatus = getOperationStatusOrThrowException(newStatus);

            operation.setOperationStatus(newOperationStatus);

            session.beginTransaction();
            session.update(operation);
            session.getTransaction().commit();

            return operation;
        }
    }
}
