package com.insurance.domain.customer.controller;

import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.customer.service.CustomerService;
import com.insurance.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<Customer> createCustomer(@RequestBody Customer customer) {
        return ApiResponse.success(customerService.createCustomer(customer));
    }

    @GetMapping("/{id}")
    public ApiResponse<Customer> getCustomer(@PathVariable Long id) {
        return ApiResponse.success(customerService.getCustomer(id));
    }

    @GetMapping
    public ApiResponse<List<Customer>> getAllCustomers() {
        return ApiResponse.success(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ApiResponse<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer update) {
        return ApiResponse.success(customerService.updateCustomer(id, update));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ApiResponse.success(null);
    }
} 