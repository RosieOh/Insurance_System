package com.insurance.service;

import com.insurance.domain.InsuranceProduct;
import com.insurance.repository.InsuranceProductRepository;
import com.core.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceProductService {
    private final InsuranceProductRepository insuranceProductRepository;

    @Transactional
    public InsuranceProduct createProduct(InsuranceProduct product) {
        return insuranceProductRepository.save(product);
    }

    @Transactional(readOnly = true)
    public InsuranceProduct getProduct(Long id) {
        return insuranceProductRepository.findById(id)
                .orElseThrow(() -> new BusinessException("보험상품을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<InsuranceProduct> getAllProducts() {
        return insuranceProductRepository.findAll();
    }

    @Transactional
    public InsuranceProduct updateProduct(Long id, InsuranceProduct update) {
        InsuranceProduct product = getProduct(id);
        product.setName(update.getName());
        product.setDescription(update.getDescription());
        product.setPremium(update.getPremium());
        product.setCoverage(update.getCoverage());
        return insuranceProductRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        InsuranceProduct product = getProduct(id);
        insuranceProductRepository.delete(product);
    }
} 