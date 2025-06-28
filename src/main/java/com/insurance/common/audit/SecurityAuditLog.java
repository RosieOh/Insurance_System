package com.insurance.common.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 보안 감사 로그 엔티티
 * 모든 보안 관련 활동을 추적하고 기록합니다.
 */
@Entity
@Table(name = "security_audit_log")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SecurityAuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String action;
    
    @Column(nullable = false)
    private String resource;
    
    @Column(name = "resource_id")
    private String resourceId;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(name = "request_method")
    private String requestMethod;
    
    @Column(name = "request_url")
    private String requestUrl;
    
    @Column(name = "request_params", columnDefinition = "TEXT")
    private String requestParams;
    
    @Column(name = "response_status")
    private Integer responseStatus;
    
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    
    @Column(name = "execution_time")
    private Long executionTime;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public SecurityAuditLog(String username, String action, String resource) {
        this.username = username;
        this.action = action;
        this.resource = resource;
    }
    
    public SecurityAuditLog(String username, String action, String resource, String resourceId) {
        this(username, action, resource);
        this.resourceId = resourceId;
    }
} 