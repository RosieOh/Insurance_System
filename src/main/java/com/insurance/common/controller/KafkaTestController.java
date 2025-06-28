package com.insurance.common.controller;

import com.insurance.Global.kafka.ContractEventProducer;
import com.insurance.common.response.ApiResponse;
import com.insurance.domain.contract.entity.Contract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * 카프카 기능을 테스트하기 위한 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/kafka-test")
@RequiredArgsConstructor
public class KafkaTestController {

    private final ContractEventProducer contractEventProducer;

    /**
     * 테스트용 계약을 생성하고 카프카 이벤트를 발행합니다.
     */
    @PostMapping("/send-contract-event")
    public ApiResponse<String> sendContractEvent(@RequestBody Map<String, Object> request) {
        try {
            // 테스트용 계약 객체 생성
            Contract contract = new Contract();
            contract.setId(999L); // 테스트용 ID
            contract.setCustomerName((String) request.get("customerName"));
            contract.setProductName((String) request.get("productName"));
            contract.setStartDate(LocalDate.parse((String) request.get("startDate")));
            contract.setEndDate(LocalDate.parse((String) request.get("endDate")));

            log.info("카프카 이벤트 발행 시작: {}", contract.getCustomerName());
            
            // 카프카 이벤트 발행
            contractEventProducer.sendContractCreatedEvent(contract);
            
            log.info("카프카 이벤트 발행 완료: {}", contract.getCustomerName());
            
            return ApiResponse.success("카프카 이벤트가 성공적으로 발행되었습니다.", 
                "고객명: " + contract.getCustomerName() + ", 상품명: " + contract.getProductName());
                
        } catch (Exception e) {
            log.error("카프카 이벤트 발행 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponse.error("카프카 이벤트 발행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 간단한 테스트 메시지를 카프카로 발행합니다.
     */
    @PostMapping("/send-test-message")
    public ApiResponse<String> sendTestMessage(@RequestBody Map<String, String> request) {
        try {
            String message = request.get("message");
            log.info("테스트 메시지 발행: {}", message);
            
            // 간단한 테스트용 계약 생성
            Contract contract = new Contract();
            contract.setId(888L);
            contract.setCustomerName("테스트 고객");
            contract.setProductName("테스트 상품");
            contract.setStartDate(LocalDate.now());
            contract.setEndDate(LocalDate.now().plusYears(1));
            
            contractEventProducer.sendContractCreatedEvent(contract);
            
            return ApiResponse.success("테스트 메시지가 성공적으로 발행되었습니다.", message);
            
        } catch (Exception e) {
            log.error("테스트 메시지 발행 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponse.error("테스트 메시지 발행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 카프카 연결 상태를 확인합니다.
     */
    @GetMapping("/health")
    public ApiResponse<String> checkKafkaHealth() {
        try {
            log.info("카프카 헬스 체크 요청");
            
            // 간단한 테스트 메시지 발행
            Contract contract = new Contract();
            contract.setId(777L);
            contract.setCustomerName("헬스체크");
            contract.setProductName("연결테스트");
            contract.setStartDate(LocalDate.now());
            contract.setEndDate(LocalDate.now().plusYears(1));
            
            contractEventProducer.sendContractCreatedEvent(contract);
            
            return ApiResponse.success("카프카 연결이 정상입니다.", "이벤트 발행 성공");
            
        } catch (Exception e) {
            log.error("카프카 헬스 체크 실패: {}", e.getMessage(), e);
            return ApiResponse.error("카프카 연결에 문제가 있습니다: " + e.getMessage());
        }
    }
} 