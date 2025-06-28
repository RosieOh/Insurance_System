package com.insurance.domain.insuranceProduct.controller;

import com.insurance.domain.insuranceProduct.entity.InsuranceProduct;
import com.insurance.domain.insuranceProduct.service.InsuranceProductService;
import com.insurance.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance-products")
@RequiredArgsConstructor
public class InsuranceProductController {
    private final InsuranceProductService insuranceProductService;

    @PostMapping
    public ApiResponse<InsuranceProduct> createProduct(@RequestBody InsuranceProduct product) {
        return ApiResponse.success(insuranceProductService.createInsuranceProduct(product));
    }

    @GetMapping("/{id}")
    public ApiResponse<InsuranceProduct> getProduct(@PathVariable Long id) {
        return ApiResponse.success(insuranceProductService.getInsuranceProduct(id));
    }

    @GetMapping
    public ApiResponse<List<InsuranceProduct>> getAllProducts() {
        return ApiResponse.success(insuranceProductService.getAllInsuranceProducts());
    }

    @PutMapping("/{id}")
    public ApiResponse<InsuranceProduct> updateProduct(@PathVariable Long id, @RequestBody InsuranceProduct update) {
        return ApiResponse.success(insuranceProductService.updateInsuranceProduct(id, update));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        insuranceProductService.deleteInsuranceProduct(id);
        return ApiResponse.success(null);
    }
} 