package com.insurance.Global.graphql;

import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.contract.service.ContractService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ContractService contractService;

    public Contract contract(Long id) {
        // GraphQL 스키마와 매핑되는 필드만 반환
        return contractService.getContract(id).orElse(null);
    }
} 