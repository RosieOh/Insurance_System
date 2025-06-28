package com.insurance.common.controller;

import com.insurance.common.annotation.SecurityAudit;
import com.insurance.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 보안 감사 로그 조회 컨트롤러
 * 보안 관련 활동 로그를 조회할 수 있습니다.
 */
@Slf4j
@RestController
@RequestMapping("/api/security/audit")
@RequiredArgsConstructor
public class SecurityAuditController {
    
    /**
     * 보안 감사 로그를 조회합니다.
     */
    @GetMapping
    @SecurityAudit(action = "AUDIT_READ", resource = "SECURITY_AUDIT")
    public ApiResponse<List<Map<String, Object>>> getAuditLogs(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String resource,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            // 실제 구현에서는 SecurityAuditLogRepository를 통해 데이터베이스에서 조회
            log.info("보안 감사 로그 조회 - 사용자: {}, 액션: {}, 리소스: {}, 기간: {} ~ {}", 
                username, action, resource, startDate, endDate);
            
            // 임시 응답 (실제로는 데이터베이스에서 조회)
            return ApiResponse.success("보안 감사 로그 조회가 완료되었습니다.", List.of());
        } catch (Exception e) {
            return ApiResponse.error("보안 감사 로그 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 특정 사용자의 보안 감사 로그를 조회합니다.
     */
    @GetMapping("/user/{username}")
    @SecurityAudit(action = "AUDIT_READ_USER", resource = "SECURITY_AUDIT", resourceIdParam = "username")
    public ApiResponse<List<Map<String, Object>>> getUserAuditLogs(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            log.info("사용자 보안 감사 로그 조회 - 사용자: {}", username);
            
            // 임시 응답 (실제로는 데이터베이스에서 조회)
            return ApiResponse.success("사용자 보안 감사 로그 조회가 완료되었습니다.", List.of());
        } catch (Exception e) {
            return ApiResponse.error("사용자 보안 감사 로그 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 특정 IP 주소의 보안 감사 로그를 조회합니다.
     */
    @GetMapping("/ip/{ipAddress}")
    @SecurityAudit(action = "AUDIT_READ_IP", resource = "SECURITY_AUDIT", resourceIdParam = "ipAddress")
    public ApiResponse<List<Map<String, Object>>> getIpAuditLogs(
            @PathVariable String ipAddress,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            log.info("IP 주소 보안 감사 로그 조회 - IP: {}", ipAddress);
            
            // 임시 응답 (실제로는 데이터베이스에서 조회)
            return ApiResponse.success("IP 주소 보안 감사 로그 조회가 완료되었습니다.", List.of());
        } catch (Exception e) {
            return ApiResponse.error("IP 주소 보안 감사 로그 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 보안 통계를 조회합니다.
     */
    @GetMapping("/statistics")
    @SecurityAudit(action = "AUDIT_STATISTICS", resource = "SECURITY_AUDIT")
    public ApiResponse<Map<String, Object>> getAuditStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        try {
            log.info("보안 감사 통계 조회 - 기간: {} ~ {}", startDate, endDate);
            
            // 임시 통계 데이터
            Map<String, Object> statistics = Map.of(
                "totalEvents", 0,
                "successEvents", 0,
                "failureEvents", 0,
                "uniqueUsers", 0,
                "uniqueIps", 0,
                "topActions", List.of(),
                "topResources", List.of()
            );
            
            return ApiResponse.success("보안 감사 통계 조회가 완료되었습니다.", statistics);
        } catch (Exception e) {
            return ApiResponse.error("보안 감사 통계 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
} 