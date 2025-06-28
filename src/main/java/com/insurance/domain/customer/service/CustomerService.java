package com.insurance.domain.customer.service;

import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.customer.repository.CustomerRepository;
import com.insurance.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("고객을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer update) {
        Customer customer = getCustomer(id);
        customer.setName(update.getName());
        customer.setEmail(update.getEmail());
        customer.setPhone(update.getPhone());
        customer.setAddress(update.getAddress());
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = getCustomer(id);
        customerRepository.delete(customer);
    }
} 