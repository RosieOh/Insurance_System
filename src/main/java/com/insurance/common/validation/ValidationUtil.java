package com.core.common.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 데이터 검증 유틸리티 클래스
 * 
 * 주요 기능:
 * - 이메일 유효성 검사
 * - 전화번호 유효성 검사
 * - 비밀번호 유효성 검사
 * - BindingResult 에러 처리
 * - 문자열 유효성 검사
 * 
 * @author core
 */
@Component
public class ValidationUtil {
    
    private static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private static final String PHONE_PATTERN = 
        "^\\d{3}-\\d{3,4}-\\d{4}$";

    /**
     * 이메일 주소의 유효성을 검사합니다.
     *
     * @param email 검사할 이메일 주소
     * @return 이메일 유효성 여부
     */
    public boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    /**
     * 전화번호의 유효성을 검사합니다.
     *
     * @param phoneNumber 검사할 전화번호
     * @return 전화번호 유효성 여부
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.matches(PHONE_PATTERN, phoneNumber);
    }

    /**
     * 비밀번호의 유효성을 검사합니다.
     * - 최소 8자, 최대 20자
     * - 대문자 포함
     * - 소문자 포함
     * - 숫자 포함
     * - 특수문자 포함
     *
     * @param password 검사할 비밀번호
     * @return 비밀번호 유효성 여부
     */
    public boolean isValidPassword(String password) {
        return password != null && 
               password.length() >= 8 && 
               password.length() <= 20 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    /**
     * BindingResult에서 발생한 검증 에러를 Map으로 변환합니다.
     *
     * @param bindingResult 검증 결과
     * @return 필드별 에러 메시지 Map
     */
    public Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        return errors;
    }

    /**
     * 문자열이 null이거나 비어있는지 검사합니다.
     *
     * @param str 검사할 문자열
     * @return null 또는 빈 문자열 여부
     */
    public boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 문자열이 숫자로만 구성되어 있는지 검사합니다.
     *
     * @param str 검사할 문자열
     * @return 숫자로만 구성된 문자열 여부
     */
    public boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d+");
    }
} 