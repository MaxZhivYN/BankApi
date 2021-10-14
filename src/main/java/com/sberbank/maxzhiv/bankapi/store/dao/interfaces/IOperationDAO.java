package com.sberbank.maxzhiv.bankapi.store.dao.interfaces;

import com.sberbank.maxzhiv.bankapi.store.entities.CardEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.OperationEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.OperationStatusEntity;
import com.sberbank.maxzhiv.bankapi.store.entities.OperationTypeEntity;

import java.util.List;

public interface IOperationDAO {
    OperationStatusEntity getOperationStatusOrThrowException(String name);
    OperationTypeEntity getOperationTypeOrThrowException(String name);
    OperationEntity createOperation(OperationStatusEntity operationStatus, OperationTypeEntity operationType, CardEntity card);
    List<OperationEntity> getAll();
    List<OperationEntity> getByStatus(String status);
    OperationEntity findOperationByIdOrThrowException(Integer id);
    Boolean isValidStatus(String status);
    OperationEntity changeStatus(OperationEntity operation, String newStatus);

}
