package com.insurance.domain.customer.repository;

import com.insurance.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 추가적인 쿼리 메서드는 필요 시 선언
} 