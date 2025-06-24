package com.core.common.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupMessage implements ApplicationListener<ContextRefreshedEvent> {
    
    private final List<String> messages = Arrays.asList(
        "김은정님 환영합니다~!!🙇‍ 다시 만나서 반가워요!😘",
        "공통 모듈에 대해서 학습을 도와줄 낭만 개발자 오레오의 AI 입니다!",
        "이 공통 모듈은 Spring Boot 3.4 기반으로 만들어졌습니다.",
        "주요 기능들을 하나씩 설명해드릴게요!",
        "1. ApiResponse: 모든 API 응답을 표준화된 형식으로 반환합니다.",
        "   - success: API 호출 성공 여부",
        "   - message: 응답 메시지",
        "   - data: 실제 응답 데이터",
        "2. BusinessException: 비즈니스 로직 예외를 처리합니다.",
        "   - 기본 에러 코드: BUSINESS_ERROR",
        "   - 커스텀 에러 코드 지정 가능",
        "3. GlobalExceptionHandler: 전역 예외 처리를 담당합니다.",
        "   - BusinessException: 400 Bad Request",
        "   - 기타 예외: 500 Internal Server Error",
        "4. @LogExecutionTime: 메소드 실행 시간을 측정합니다.",
        "   - 메소드 실행 시작/종료 시간 기록",
        "   - 실행 시간을 밀리초 단위로 로깅",
        "이 모듈을 사용하면 일관된 API 응답 형식과 예외 처리가 가능합니다.",
        "다른 프로젝트에서도 쉽게 재사용할 수 있도록 설계되었습니다.",
        "추가 기능이나 수정이 필요하시다면 말씀해 주세요! 😊"
    );

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            for (String message : messages) {
                System.out.println(message);
                try {
                    Thread.sleep(2000); // 2초 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
} 