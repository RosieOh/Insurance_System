package com.insurance.common.util;

import com.insurance.common.dto.MapDataRequest;
import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.insuranceProduct.entity.InsuranceProduct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * HashMap 데이터를 엔티티로 변환하는 공통 유틸리티
 * 다양한 엔티티 타입에 대한 변환 로직을 제공합니다.
 */
@Component
public class MapDataConverter {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * MapDataRequest를 Contract 엔티티로 변환합니다.
     */
    public Contract convertToContract(MapDataRequest request) {
        Map<String, Object> data = request.getData();
        Contract contract = new Contract();
        
        if (data != null) {
            contract.setCustomerName(getStringValue(data, "customerName"));
            contract.setProductName(getStringValue(data, "productName"));
            contract.setStartDate(getDateValue(data, "startDate"));
            contract.setEndDate(getDateValue(data, "endDate"));
        }
        
        return contract;
    }
    
    /**
     * MapDataRequest를 Customer 엔티티로 변환합니다.
     */
    public Customer convertToCustomer(MapDataRequest request) {
        Map<String, Object> data = request.getData();
        Customer customer = new Customer();
        
        if (data != null) {
            customer.setName(getStringValue(data, "name"));
            customer.setEmail(getStringValue(data, "email"));
            customer.setPhone(getStringValue(data, "phone"));
            customer.setAddress(getStringValue(data, "address"));
        }
        
        return customer;
    }
    
    /**
     * MapDataRequest를 InsuranceProduct 엔티티로 변환합니다.
     */
    public InsuranceProduct convertToInsuranceProduct(MapDataRequest request) {
        Map<String, Object> data = request.getData();
        InsuranceProduct product = new InsuranceProduct();
        
        if (data != null) {
            product.setName(getStringValue(data, "name"));
            product.setDescription(getStringValue(data, "description"));
            product.setPremium(getBigDecimalValue(data, "premium"));
            product.setCoverage(getStringValue(data, "coverage"));
        }
        
        return product;
    }
    
    /**
     * 엔티티를 Map으로 변환합니다.
     */
    public Map<String, Object> convertEntityToMap(Object entity) {
        if (entity instanceof Contract) {
            return convertContractToMap((Contract) entity);
        } else if (entity instanceof Customer) {
            return convertCustomerToMap((Customer) entity);
        } else if (entity instanceof InsuranceProduct) {
            return convertInsuranceProductToMap((InsuranceProduct) entity);
        }
        return null;
    }
    
    private Map<String, Object> convertContractToMap(Contract contract) {
        return Map.of(
            "id", contract.getId(),
            "customerName", contract.getCustomerName(),
            "productName", contract.getProductName(),
            "startDate", contract.getStartDate() != null ? contract.getStartDate().toString() : null,
            "endDate", contract.getEndDate() != null ? contract.getEndDate().toString() : null
        );
    }
    
    private Map<String, Object> convertCustomerToMap(Customer customer) {
        return Map.of(
            "id", customer.getId(),
            "name", customer.getName(),
            "email", customer.getEmail(),
            "phone", customer.getPhone(),
            "address", customer.getAddress()
        );
    }
    
    private Map<String, Object> convertInsuranceProductToMap(InsuranceProduct product) {
        return Map.of(
            "id", product.getId(),
            "name", product.getName(),
            "description", product.getDescription(),
            "premium", product.getPremium(),
            "coverage", product.getCoverage()
        );
    }
    
    // 헬퍼 메서드들
    private String getStringValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }
    
    private LocalDate getDateValue(Map<String, Object> data, String key) {
        String dateStr = getStringValue(data, key);
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    private BigDecimal getBigDecimalValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        return value != null ? new BigDecimal(value.toString()) : null;
    }
} 