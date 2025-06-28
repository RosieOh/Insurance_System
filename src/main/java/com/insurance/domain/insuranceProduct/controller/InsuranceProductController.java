package com.insurance.controller;

import com.insurance.domain.InsuranceProduct;
import com.insurance.service.InsuranceProductService;
import com.core.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class InsuranceProductController {
    private final InsuranceProductService insuranceProductService;

    @PostMapping
    public ApiResponse<InsuranceProduct> createProduct(@RequestBody InsuranceProduct product) {
        return ApiResponse.success(insuranceProductService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ApiResponse<InsuranceProduct> getProduct(@PathVariable Long id) {
        return ApiResponse.success(insuranceProductService.getProduct(id));
    }

    @GetMapping
    public ApiResponse<List<InsuranceProduct>> getAllProducts() {
        return ApiResponse.success(insuranceProductService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ApiResponse<InsuranceProduct> updateProduct(@PathVariable Long id, @RequestBody InsuranceProduct update) {
        return ApiResponse.success(insuranceProductService.updateProduct(id, update));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        insuranceProductService.deleteProduct(id);
        return ApiResponse.success(null);
    }
} 