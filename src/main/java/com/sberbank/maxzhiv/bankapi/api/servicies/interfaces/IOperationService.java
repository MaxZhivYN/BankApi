package com.sberbank.maxzhiv.bankapi.api.servicies.interfaces;

import com.sberbank.maxzhiv.bankapi.api.dto.ChangeStatusDto;
import com.sberbank.maxzhiv.bankapi.api.dto.OperationDto;

import java.util.List;

public interface IOperationService {
    List<OperationDto> getAll();
    List<OperationDto> getByStatus(String status);
    OperationDto changeStatus(Integer operationId, ChangeStatusDto changeStatusDto);
}
