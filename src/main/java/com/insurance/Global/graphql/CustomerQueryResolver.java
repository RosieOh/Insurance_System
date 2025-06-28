package com.insurance.Global.graphql;

// import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerQueryResolver /* implements GraphQLQueryResolver */ {
    @Autowired
    private CustomerService customerService;

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Customer getCustomer(Long id) {
        return customerService.getCustomer(id);
    }
} 