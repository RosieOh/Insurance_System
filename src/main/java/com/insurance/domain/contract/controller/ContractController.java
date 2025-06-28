package com.insurance.domain.contract.controller;

import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.contract.service.ContractService;
import com.insurance.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @PostMapping
    public ApiResponse<Contract> createContract(@RequestBody Contract contract) {
        Contract saved = contractService.createContract(contract);
        return ApiResponse.success(saved);
    }

    @GetMapping("/{id}")
    public ApiResponse<Contract> getContract(@PathVariable Long id) {
        Optional<Contract> contract = contractService.getContract(id);
        return contract.map(ApiResponse::success)
                .orElseGet(() -> ApiResponse.error("계약을 찾을 수 없습니다."));
    }
} 