package com.insurance.common.aspect;

import com.insurance.common.annotation.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 메서드 실행 시간을 측정하는 AOP
 * @LogExecutionTime 어노테이션이 붙은 메서드의 실행 시간을 로깅합니다.
 */
@Slf4j
@Aspect
@Component
public class LogExecutionTimeAspect {

    /**
     * @LogExecutionTime 어노테이션이 적용된 메소드의 실행 시간을 측정하고 로깅합니다.
     * 
     * @param joinPoint 실행 중인 메소드의 정보를 담고 있는 JoinPoint
     * @param logExecutionTime @LogExecutionTime 어노테이션 정보
     * @return 원본 메소드의 실행 결과
     * @throws Throwable 메소드 실행 중 발생한 예외
     */
    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.info("Method: {} - Execution Time: {}ms", 
                joinPoint.getSignature().getName(), executionTime);
            
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.error("Method: {} - Execution Time: {}ms - Error: {}", 
                joinPoint.getSignature().getName(), executionTime, e.getMessage());
            
            throw e;
        }
    }
} 