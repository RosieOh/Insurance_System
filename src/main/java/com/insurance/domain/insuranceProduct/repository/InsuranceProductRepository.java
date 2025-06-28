package com.insurance.domain.insuranceProduct.repository;

import com.insurance.domain.insuranceProduct.entity.InsuranceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {
    // 추가적인 쿼리 메서드는 필요 시 선언
} 