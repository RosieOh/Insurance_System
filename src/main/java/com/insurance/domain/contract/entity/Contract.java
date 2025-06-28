package com.insurance.domain.contract.entity;

import com.insurance.common.audit.AuditEntity;
import com.insurance.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
public class Contract extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;

    public Contract(String customerName, String productName, LocalDate startDate, LocalDate endDate) {
        this.customerName = customerName;
        this.productName = productName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
} 