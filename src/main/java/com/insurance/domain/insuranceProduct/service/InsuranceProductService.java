package com.insurance.domain.insuranceProduct.service;

import com.insurance.domain.insuranceProduct.entity.InsuranceProduct;
import com.insurance.domain.insuranceProduct.repository.InsuranceProductRepository;
import com.insurance.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceProductService {
    private final InsuranceProductRepository insuranceProductRepository;

    @Transactional
    public InsuranceProduct createInsuranceProduct(InsuranceProduct product) {
        return insuranceProductRepository.save(product);
    }

    @Transactional(readOnly = true)
    public InsuranceProduct getInsuranceProduct(Long id) {
        return insuranceProductRepository.findById(id)
                .orElseThrow(() -> new BusinessException("보험상품을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<InsuranceProduct> getAllInsuranceProducts() {
        return insuranceProductRepository.findAll();
    }

    @Transactional
    public InsuranceProduct updateInsuranceProduct(Long id, InsuranceProduct update) {
        InsuranceProduct product = getInsuranceProduct(id);
        product.setName(update.getName());
        product.setDescription(update.getDescription());
        product.setPremium(update.getPremium());
        product.setCoverage(update.getCoverage());
        return insuranceProductRepository.save(product);
    }

    @Transactional
    public void deleteInsuranceProduct(Long id) {
        InsuranceProduct product = getInsuranceProduct(id);
        insuranceProductRepository.delete(product);
    }
} 