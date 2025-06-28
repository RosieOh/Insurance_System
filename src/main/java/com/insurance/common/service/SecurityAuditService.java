package com.insurance.common.service;

import com.insurance.common.audit.SecurityAuditLog;
import com.insurance.common.security.WebRemoteAddrDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * 보안 감사 서비스
 * 모든 보안 관련 활동을 로깅하고 추적합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityAuditService {
    
    /**
     * 보안 감사 로그를 기록합니다.
     */
    public void logSecurityEvent(String username, String action, String resource) {
        logSecurityEvent(username, action, resource, null, null);
    }
    
    /**
     * 보안 감사 로그를 기록합니다 (리소스 ID 포함).
     */
    public void logSecurityEvent(String username, String action, String resource, String resourceId) {
        logSecurityEvent(username, action, resource, resourceId, null);
    }
    
    /**
     * 보안 감사 로그를 기록합니다 (상세 정보 포함).
     */
    public void logSecurityEvent(String username, String action, String resource, String resourceId, Map<String, Object> additionalData) {
        try {
            HttpServletRequest request = getCurrentRequest();
            if (request != null) {
                WebRemoteAddrDetails details = new WebRemoteAddrDetails(request);
                
                SecurityAuditLog auditLog = new SecurityAuditLog(username, action, resource, resourceId);
                auditLog.setIpAddress(details.getRemoteAddress());
                auditLog.setUserAgent(details.getUserAgent());
                auditLog.setSessionId(details.getSessionId());
                auditLog.setRequestMethod(request.getMethod());
                auditLog.setRequestUrl(request.getRequestURI());
                auditLog.setRequestParams(extractRequestParams(request));
                
                if (additionalData != null) {
                    // 추가 데이터를 JSON 형태로 저장할 수 있음
                    auditLog.setRequestParams(auditLog.getRequestParams() + " | Additional: " + additionalData.toString());
                }
                
                // 실제 구현에서는 SecurityAuditLogRepository를 통해 저장
                log.info("Security Audit: {} - {} - {} - IP: {} - UserAgent: {}", 
                    username, action, resource, details.getRemoteAddress(), details.getUserAgent());
            }
        } catch (Exception e) {
            log.error("보안 감사 로그 기록 중 오류 발생", e);
        }
    }
    
    /**
     * 로그인 시도를 기록합니다.
     */
    public void logLoginAttempt(String username, boolean success, String errorMessage) {
        String action = success ? "LOGIN_SUCCESS" : "LOGIN_FAILURE";
        logSecurityEvent(username, action, "AUTHENTICATION");
        
        if (!success && errorMessage != null) {
            log.warn("로그인 실패 - 사용자: {}, IP: {}, 오류: {}", 
                username, getCurrentIpAddress(), errorMessage);
        }
    }
    
    /**
     * 로그아웃을 기록합니다.
     */
    public void logLogout(String username) {
        logSecurityEvent(username, "LOGOUT", "AUTHENTICATION");
    }
    
    /**
     * 데이터 접근을 기록합니다.
     */
    public void logDataAccess(String username, String resource, String resourceId, String operation) {
        logSecurityEvent(username, operation, resource, resourceId);
    }
    
    /**
     * 현재 HTTP 요청을 가져옵니다.
     */
    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 현재 IP 주소를 가져옵니다.
     */
    private String getCurrentIpAddress() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            WebRemoteAddrDetails details = new WebRemoteAddrDetails(request);
            return details.getRemoteAddress();
        }
        return "unknown";
    }
    
    /**
     * 요청 파라미터를 추출합니다.
     */
    private String extractRequestParams(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        request.getParameterMap().forEach((key, values) -> {
            if (params.length() > 0) params.append("&");
            params.append(key).append("=").append(String.join(",", values));
        });
        return params.toString();
    }
} 