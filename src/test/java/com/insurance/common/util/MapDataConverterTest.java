package com.insurance.common.util;

import com.insurance.common.dto.MapDataRequest;
import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.insuranceProduct.entity.InsuranceProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class MapDataConverterTest {

    private MapDataConverter converter;

    @BeforeEach
    void setUp() {
        converter = new MapDataConverter();
    }

    @Test
    void testConvertToContract() {
        // Given
        Map<String, Object> data = new HashMap<>();
        data.put("customerName", "홍길동");
        data.put("productName", "자동차보험");
        data.put("startDate", "2024-01-01");
        data.put("endDate", "2024-12-31");

        MapDataRequest request = new MapDataRequest(data, "contract");

        // When
        Contract contract = converter.convertToContract(request);

        // Then
        assertNotNull(contract);
        assertEquals("홍길동", contract.getCustomerName());
        assertEquals("자동차보험", contract.getProductName());
        assertEquals(LocalDate.of(2024, 1, 1), contract.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), contract.getEndDate());
    }

    @Test
    void testConvertToCustomer() {
        // Given
        Map<String, Object> data = new HashMap<>();
        data.put("name", "김철수");
        data.put("email", "kim@example.com");
        data.put("phone", "010-1234-5678");
        data.put("address", "서울시 강남구");

        MapDataRequest request = new MapDataRequest(data, "customer");

        // When
        Customer customer = converter.convertToCustomer(request);

        // Then
        assertNotNull(customer);
        assertEquals("김철수", customer.getName());
        assertEquals("kim@example.com", customer.getEmail());
        assertEquals("010-1234-5678", customer.getPhone());
        assertEquals("서울시 강남구", customer.getAddress());
    }

    @Test
    void testConvertToInsuranceProduct() {
        // Given
        Map<String, Object> data = new HashMap<>();
        data.put("name", "자동차보험");
        data.put("description", "자동차 사고 보상");
        data.put("premium", 500000.00);
        data.put("coverage", "사고, 도난, 자연재해");

        MapDataRequest request = new MapDataRequest(data, "insuranceproduct");

        // When
        InsuranceProduct product = converter.convertToInsuranceProduct(request);

        // Then
        assertNotNull(product);
        assertEquals("자동차보험", product.getName());
        assertEquals("자동차 사고 보상", product.getDescription());
        assertEquals(new BigDecimal("500000.00"), product.getPremium());
        assertEquals("사고, 도난, 자연재해", product.getCoverage());
    }

    @Test
    void testConvertEntityToMap() {
        // Given
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setCustomerName("홍길동");
        contract.setProductName("자동차보험");
        contract.setStartDate(LocalDate.of(2024, 1, 1));
        contract.setEndDate(LocalDate.of(2024, 12, 31));

        // When
        Map<String, Object> result = converter.convertEntityToMap(contract);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.get("id"));
        assertEquals("홍길동", result.get("customerName"));
        assertEquals("자동차보험", result.get("productName"));
        assertEquals("2024-01-01", result.get("startDate"));
        assertEquals("2024-12-31", result.get("endDate"));
    }

    @Test
    void testNullDataHandling() {
        // Given
        MapDataRequest request = new MapDataRequest(null, "contract");

        // When
        Contract contract = converter.convertToContract(request);

        // Then
        assertNotNull(contract);
        assertNull(contract.getCustomerName());
        assertNull(contract.getProductName());
        assertNull(contract.getStartDate());
        assertNull(contract.getEndDate());
    }
} 