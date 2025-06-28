package com.insurance.common.exception;

import com.insurance.common.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리를 위한 핸들러 클래스
 * 애플리케이션에서 발생하는 예외들을 일관된 형식으로 처리합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BusinessException을 처리합니다.
     * HTTP 400 Bad Request 상태 코드와 함께 에러 응답을 반환합니다.
     *
     * @param ex 발생한 BusinessException
     * @return 에러 응답을 포함한 ApiResponse
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    /**
     * 처리되지 않은 모든 예외를 처리합니다.
     * HTTP 500 Internal Server Error 상태 코드와 함께 에러 응답을 반환합니다.
     *
     * @param ex 발생한 예외
     * @return 에러 응답을 포함한 ApiResponse
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {
        return ApiResponse.error("알 수 없는 오류가 발생했습니다.");
    }
} 