package com.sberbank.maxzhiv.bankapi.api.controllers;

import com.sberbank.maxzhiv.bankapi.api.dto.ChangeStatusDto;
import com.sberbank.maxzhiv.bankapi.api.dto.OperationDto;
import com.sberbank.maxzhiv.bankapi.api.servicies.interfaces.IOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class OperationController {
    private final IOperationService operationService;

    private static final String GET_ALL = "operations";
    private static final String GET_AWAITING = "operations/awaiting";
    private static final String GET_SUCCESS = "operations/success";
    private static final String CHANGE_STATUS = "operations/{operation_id}";

    @GetMapping(GET_ALL)
    public List<OperationDto> getAll() {
        return operationService.getAll();
    }

    @GetMapping(GET_AWAITING)
    public List<OperationDto> getAwaiting() {
        return operationService.getByStatus("AWAITING");
    }

    @GetMapping(GET_SUCCESS)
    public List<OperationDto> getSuccess() {
        return operationService.getByStatus("SUCCESS");
    }

    @PatchMapping(CHANGE_STATUS)
    public OperationDto changeStatus(
            @PathVariable("operation_id") Integer operationId,
            @RequestBody ChangeStatusDto changeStatusDto) {

        return operationService.changeStatus(operationId, changeStatusDto);
    }

}
