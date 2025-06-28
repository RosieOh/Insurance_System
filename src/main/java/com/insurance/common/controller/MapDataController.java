package com.insurance.common.controller;

import com.insurance.common.annotation.SecurityAudit;
import com.insurance.common.dto.MapDataRequest;
import com.insurance.common.response.ApiResponse;
import com.insurance.common.util.MapDataConverter;
import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.contract.service.ContractService;
import com.insurance.domain.customer.entity.Customer;
import com.insurance.domain.customer.service.CustomerService;
import com.insurance.domain.insuranceProduct.entity.InsuranceProduct;
import com.insurance.domain.insuranceProduct.service.InsuranceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HashMap 형태의 데이터를 처리하는 공통 컨트롤러
 * 모든 엔티티에 대한 CRUD 작업을 HashMap으로 처리할 수 있습니다.
 */
@RestController
@RequestMapping("/api/map-data")
@RequiredArgsConstructor
public class MapDataController {
    
    private final MapDataConverter mapDataConverter;
    private final ContractService contractService;
    private final CustomerService customerService;
    private final InsuranceProductService insuranceProductService;
    
    /**
     * HashMap 데이터로 엔티티를 생성합니다.
     */
    @PostMapping("/{entityType}")
    @SecurityAudit(action = "CREATE", resource = "ENTITY", resourceIdParam = "entityType", includeRequestData = true)
    public ApiResponse<Map<String, Object>> createEntity(
            @PathVariable String entityType,
            @RequestBody MapDataRequest request) {
        
        try {
            Object entity = createEntityByType(entityType, request);
            Map<String, Object> result = mapDataConverter.convertEntityToMap(entity);
            return ApiResponse.success("엔티티가 성공적으로 생성되었습니다.", result);
        } catch (Exception e) {
            return ApiResponse.error("엔티티 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * HashMap 데이터로 엔티티를 업데이트합니다.
     */
    @PutMapping("/{entityType}/{id}")
    @SecurityAudit(action = "UPDATE", resource = "ENTITY", resourceIdParam = "id", includeRequestData = true)
    public ApiResponse<Map<String, Object>> updateEntity(
            @PathVariable String entityType,
            @PathVariable Long id,
            @RequestBody MapDataRequest request) {
        
        try {
            Object entity = updateEntityByType(entityType, id, request);
            Map<String, Object> result = mapDataConverter.convertEntityToMap(entity);
            return ApiResponse.success("엔티티가 성공적으로 업데이트되었습니다.", result);
        } catch (Exception e) {
            return ApiResponse.error("엔티티 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * ID로 엔티티를 조회합니다.
     */
    @GetMapping("/{entityType}/{id}")
    @SecurityAudit(action = "READ", resource = "ENTITY", resourceIdParam = "id")
    public ApiResponse<Map<String, Object>> getEntity(
            @PathVariable String entityType,
            @PathVariable Long id) {
        
        try {
            Optional<Object> entity = getEntityByType(entityType, id);
            if (entity.isPresent()) {
                Map<String, Object> result = mapDataConverter.convertEntityToMap(entity.get());
                return ApiResponse.success(result);
            } else {
                return ApiResponse.error("엔티티를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ApiResponse.error("엔티티 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 모든 엔티티를 조회합니다.
     */
    @GetMapping("/{entityType}")
    @SecurityAudit(action = "READ_ALL", resource = "ENTITY", resourceIdParam = "entityType")
    public ApiResponse<List<Map<String, Object>>> getAllEntities(
            @PathVariable String entityType) {
        
        try {
            List<Object> entities = getAllEntitiesByType(entityType);
            List<Map<String, Object>> results = entities.stream()
                    .map(mapDataConverter::convertEntityToMap)
                    .toList();
            return ApiResponse.success(results);
        } catch (Exception e) {
            return ApiResponse.error("엔티티 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 엔티티를 삭제합니다.
     */
    @DeleteMapping("/{entityType}/{id}")
    @SecurityAudit(action = "DELETE", resource = "ENTITY", resourceIdParam = "id")
    public ApiResponse<Void> deleteEntity(
            @PathVariable String entityType,
            @PathVariable Long id) {
        
        try {
            deleteEntityByType(entityType, id);
            return ApiResponse.success("엔티티가 성공적으로 삭제되었습니다.", null);
        } catch (Exception e) {
            return ApiResponse.error("엔티티 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 헬퍼 메서드들
    private Object createEntityByType(String entityType, MapDataRequest request) {
        return switch (entityType.toLowerCase()) {
            case "contract" -> contractService.createContract(mapDataConverter.convertToContract(request));
            case "customer" -> customerService.createCustomer(mapDataConverter.convertToCustomer(request));
            case "insuranceproduct" -> insuranceProductService.createInsuranceProduct(mapDataConverter.convertToInsuranceProduct(request));
            default -> throw new IllegalArgumentException("지원하지 않는 엔티티 타입입니다: " + entityType);
        };
    }
    
    private Object updateEntityByType(String entityType, Long id, MapDataRequest request) {
        return switch (entityType.toLowerCase()) {
            case "contract" -> {
                Contract contract = mapDataConverter.convertToContract(request);
                contract.setId(id);
                yield contractService.updateContract(id, contract);
            }
            case "customer" -> {
                Customer customer = mapDataConverter.convertToCustomer(request);
                customer.setId(id);
                yield customerService.updateCustomer(id, customer);
            }
            case "insuranceproduct" -> {
                InsuranceProduct product = mapDataConverter.convertToInsuranceProduct(request);
                product.setId(id);
                yield insuranceProductService.updateInsuranceProduct(id, product);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 엔티티 타입입니다: " + entityType);
        };
    }
    
    private Optional<Object> getEntityByType(String entityType, Long id) {
        return switch (entityType.toLowerCase()) {
            case "contract" -> contractService.getContract(id).map(contract -> (Object) contract);
            case "customer" -> Optional.of(customerService.getCustomer(id));
            case "insuranceproduct" -> Optional.of(insuranceProductService.getInsuranceProduct(id));
            default -> throw new IllegalArgumentException("지원하지 않는 엔티티 타입입니다: " + entityType);
        };
    }
    
    private List<Object> getAllEntitiesByType(String entityType) {
        return switch (entityType.toLowerCase()) {
            case "contract" -> contractService.getAllContracts().stream().map(contract -> (Object) contract).toList();
            case "customer" -> customerService.getAllCustomers().stream().map(customer -> (Object) customer).toList();
            case "insuranceproduct" -> insuranceProductService.getAllInsuranceProducts().stream().map(product -> (Object) product).toList();
            default -> throw new IllegalArgumentException("지원하지 않는 엔티티 타입입니다: " + entityType);
        };
    }
    
    private void deleteEntityByType(String entityType, Long id) {
        switch (entityType.toLowerCase()) {
            case "contract" -> contractService.deleteContract(id);
            case "customer" -> customerService.deleteCustomer(id);
            case "insuranceproduct" -> insuranceProductService.deleteInsuranceProduct(id);
            default -> throw new IllegalArgumentException("지원하지 않는 엔티티 타입입니다: " + entityType);
        }
    }
} 