package com.insurance.Global.graphql;

import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.customer.service.CustomerService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private CustomerService customerService;

    public Customer customer(Long id) {
        // GraphQL 스키마와 매핑되는 필드만 반환
        return customerService.getCustomer(id);
    }
} 