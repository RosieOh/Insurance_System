package com.insurance.Global.graphql;

// import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.contract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContractQueryResolver /* implements GraphQLQueryResolver */ {
    @Autowired
    private ContractService contractService;

    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    public Contract getContract(Long id) {
        return contractService.getContract(id).orElse(null);
    }
} 