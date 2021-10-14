package com.sberbank.maxzhiv.bankapi.api.servicies.implementation;

import com.sberbank.maxzhiv.bankapi.api.dto.ChangeStatusDto;
import com.sberbank.maxzhiv.bankapi.api.dto.OperationDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IOperationService;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.IOperationDAO;
import com.sberbank.maxzhiv.bankapi.store.entities.OperationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService implements IOperationService {
    private final IOperationDAO operationDAO;

    @Override
    public List<OperationDto> getAll() {
        List<OperationEntity> operations = operationDAO.getAll();

        return operations.stream()
                .map(this::makeOperationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationDto> getByStatus(String status) {
        List<OperationEntity> operations = operationDAO.getByStatus(status);

        return operations.stream()
                .map(this::makeOperationDto)
                .collect(Collectors.toList());
    }

    @Override
    public OperationDto changeStatus(Integer operationId, ChangeStatusDto changeStatusDto) {
        String newStatus = changeStatusDto.getNewStatus();

        if (Objects.isNull(newStatus)) {
            throw new BadRequestException("'newStatus' need to be not empty");
        }

        if (!operationDAO.isValidStatus(newStatus)) {
            throw new NotFoundException(String.format("Status %s not found", newStatus));
        }

        OperationEntity operation = operationDAO.findOperationByIdOrThrowException(operationId);

        OperationEntity changedStatusOperation = operationDAO.changeStatus(operation, newStatus);

        return makeOperationDto(changedStatusOperation);
    }

    private OperationDto makeOperationDto(OperationEntity operationEntity) {
        return OperationDto.builder()
                .id(operationEntity.getId())
                .operationType(operationEntity.getOperationType().getName())
                .operationStatus(operationEntity.getOperationStatus().getName())
                .cardNumber(operationEntity.getCard().getNumber())
                .build();
    }
}
