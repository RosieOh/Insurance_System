package com.insurance.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

/**
 * HashMap 형태의 데이터를 받기 위한 공통 DTO
 * 유연한 데이터 구조를 지원하여 다양한 형태의 요청을 처리할 수 있습니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class MapDataRequest {
    
    /**
     * 요청 데이터를 담는 HashMap
     */
    private Map<String, Object> data;
    
    /**
     * 요청 메타데이터 (선택사항)
     */
    private Map<String, Object> metadata;
    
    /**
     * 요청 타입 (엔티티 타입 구분용)
     */
    private String requestType;
    
    public MapDataRequest(Map<String, Object> data) {
        this.data = data;
    }
    
    public MapDataRequest(Map<String, Object> data, String requestType) {
        this.data = data;
        this.requestType = requestType;
    }
    
    /**
     * 특정 키의 값을 가져옵니다.
     */
    public Object getValue(String key) {
        return data != null ? data.get(key) : null;
    }
    
    /**
     * 특정 키의 값을 String으로 가져옵니다.
     */
    public String getStringValue(String key) {
        Object value = getValue(key);
        return value != null ? value.toString() : null;
    }
    
    /**
     * 특정 키의 값을 Long으로 가져옵니다.
     */
    public Long getLongValue(String key) {
        Object value = getValue(key);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return value != null ? Long.valueOf(value.toString()) : null;
    }
    
    /**
     * 특정 키의 값을 Integer로 가져옵니다.
     */
    public Integer getIntegerValue(String key) {
        Object value = getValue(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return value != null ? Integer.valueOf(value.toString()) : null;
    }
    
    /**
     * 특정 키의 값을 Boolean으로 가져옵니다.
     */
    public Boolean getBooleanValue(String key) {
        Object value = getValue(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return value != null ? Boolean.valueOf(value.toString()) : null;
    }
} 