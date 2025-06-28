package com.insurance.common.aspect;

import com.insurance.common.annotation.SecurityAudit;
import com.insurance.common.service.SecurityAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 보안 감사 AOP
 * @SecurityAudit 어노테이션이 붙은 메서드의 실행을 감사합니다.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAuditAspect {
    
    private final SecurityAuditService securityAuditService;
    
    @Around("@annotation(com.insurance.common.annotation.SecurityAudit)")
    public Object auditSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SecurityAudit auditAnnotation = method.getAnnotation(SecurityAudit.class);
        
        String username = getCurrentUsername();
        String action = auditAnnotation.action();
        String resource = auditAnnotation.resource();
        String resourceId = extractResourceId(joinPoint, method, auditAnnotation);
        Map<String, Object> additionalData = null;
        
        if (auditAnnotation.includeRequestData()) {
            additionalData = extractRequestData(joinPoint, method);
        }
        
        long startTime = System.currentTimeMillis();
        Object result = null;
        boolean success = true;
        
        try {
            // 메서드 실행
            result = joinPoint.proceed();
            
            // 성공 로그
            securityAuditService.logSecurityEvent(username, action, resource, resourceId, additionalData);
            
        } catch (Exception e) {
            success = false;
            // 실패 로그
            securityAuditService.logSecurityEvent(username, action + "_FAILURE", resource, resourceId, additionalData);
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            log.debug("Security Audit - Method: {}, Execution Time: {}ms, Success: {}", 
                method.getName(), executionTime, success);
        }
        
        return result;
    }
    
    /**
     * 현재 인증된 사용자명을 가져옵니다.
     */
    private String getCurrentUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                return authentication.getName();
            }
        } catch (Exception e) {
            log.warn("사용자 인증 정보를 가져올 수 없습니다.", e);
        }
        return "anonymous";
    }
    
    /**
     * 리소스 ID를 추출합니다.
     */
    private String extractResourceId(ProceedingJoinPoint joinPoint, Method method, SecurityAudit auditAnnotation) {
        String resourceIdParam = auditAnnotation.resourceIdParam();
        if (resourceIdParam.isEmpty()) {
            return null;
        }
        
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals(resourceIdParam)) {
                return args[i] != null ? args[i].toString() : null;
            }
        }
        
        return null;
    }
    
    /**
     * 요청 데이터를 추출합니다.
     */
    private Map<String, Object> extractRequestData(ProceedingJoinPoint joinPoint, Method method) {
        Map<String, Object> requestData = new HashMap<>();
        
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        
        for (int i = 0; i < parameters.length; i++) {
            String paramName = parameters[i].getName();
            Object paramValue = args[i];
            
            // 민감한 정보는 제외
            if (!isSensitiveParameter(paramName)) {
                requestData.put(paramName, paramValue);
            }
        }
        
        return requestData;
    }
    
    /**
     * 민감한 파라미터인지 확인합니다.
     */
    private boolean isSensitiveParameter(String paramName) {
        String lowerParamName = paramName.toLowerCase();
        return lowerParamName.contains("password") || 
               lowerParamName.contains("token") || 
               lowerParamName.contains("secret") ||
               lowerParamName.contains("key");
    }
} 