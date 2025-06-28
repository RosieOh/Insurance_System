package com.insurance.Global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.domain.contract.entity.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 계약 이벤트를 수신하는 카프카 컨슈머
 * 실제로 메시지가 전송되는지 확인할 수 있습니다.
 */
@Slf4j
@Component
public class ContractEventConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "contract-events", groupId = "insurance-group")
    public void consumeContractEvent(String contractJson) {
        try {
            log.info("=== 카프카 이벤트 수신 ===");
            log.info("수신된 메시지: {}", contractJson);
            
            // JSON을 Contract 객체로 변환
            Contract contract = objectMapper.readValue(contractJson, Contract.class);
            log.info("계약 정보: ID={}, 고객명={}, 상품명={}, 시작일={}, 종료일={}", 
                contract.getId(), 
                contract.getCustomerName(), 
                contract.getProductName(), 
                contract.getStartDate(), 
                contract.getEndDate());
            
            // 알림 발송, 통계 업데이트, 외부 시스템 연동 추가하자 
            
            log.info("=== 카프카 이벤트 처리 완료 ===");
            
        } catch (Exception e) {
            log.error("카프카 이벤트 처리 중 오류 발생: {}", e.getMessage(), e);
        }
    }
} 