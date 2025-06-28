package com.insurance.domain.contract.service;

import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.contract.repository.ContractRepository;
import com.insurance.common.annotation.LogExecutionTime;
import com.insurance.Global.kafka.ContractEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractEventProducer contractEventProducer;

    @LogExecutionTime
    public Contract createContract(Contract contract) {
        Contract saved = contractRepository.save(contract);
        contractEventProducer.sendContractCreatedEvent(saved);
        return saved;
    }

    public Optional<Contract> getContract(Long id) {
        return contractRepository.findById(id);
    }
    
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    
    public Contract updateContract(Long id, Contract contract) {
        if (contractRepository.existsById(id)) {
            contract.setId(id);
            return contractRepository.save(contract);
        }
        throw new RuntimeException("계약을 찾을 수 없습니다: " + id);
    }
    
    public void deleteContract(Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
        } else {
            throw new RuntimeException("계약을 찾을 수 없습니다: " + id);
        }
    }
} 