package com.insurance.Global.kafka;

import com.insurance.domain.contract.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContractEventProducer {
    private static final String TOPIC = "contract-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public void sendContractCreatedEvent(Contract contract) {
        try {
            String contractJson = objectMapper.writeValueAsString(contract);
            kafkaTemplate.send(TOPIC, contractJson);
        } catch (Exception e) {
            // 로깅 또는 예외 처리
            e.printStackTrace();
        }
    }
} 