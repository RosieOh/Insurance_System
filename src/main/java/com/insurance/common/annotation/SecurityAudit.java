package com.insurance.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 보안 감사를 위한 어노테이션
 * 메서드에 이 어노테이션을 붙이면 자동으로 보안 감사 로그가 기록됩니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityAudit {
    
    /**
     * 감사할 액션 이름
     */
    String action() default "";
    
    /**
     * 감사할 리소스 이름
     */
    String resource() default "";
    
    /**
     * 리소스 ID를 가져올 파라미터 이름
     */
    String resourceIdParam() default "";
    
    /**
     * 사용자명을 가져올 파라미터 이름
     */
    String usernameParam() default "";
    
    /**
     * 추가 데이터를 포함할지 여부
     */
    boolean includeRequestData() default false;
} 