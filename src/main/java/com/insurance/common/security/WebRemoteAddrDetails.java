package com.insurance.common.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * 웹 요청의 원격 주소와 사용자 에이전트 정보를 추출하는 클래스
 * 보안 감사 및 로깅에 활용됩니다.
 */
@Getter
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WebRemoteAddrDetails extends WebAuthenticationDetails {
    private final String userAgent;
    
    public WebRemoteAddrDetails(HttpServletRequest request) {
        this(extractRemoteAddr(request), extractSessionId(request), extractUserAgent(request));
    }

    public WebRemoteAddrDetails(String remoteAddress, String sessionId, String userAgent) {
        super(remoteAddress, sessionId);
        this.userAgent = userAgent;
    }

    private static String extractSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getId() : null;
    }

    private static String extractRemoteAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) ipAddress = request.getHeader("Proxy-Client-IP");
        if (ipAddress == null) ipAddress = request.getHeader("WL-Proxy-Client-IP");
        if (ipAddress == null) ipAddress = request.getHeader("HTTP_CLIENT_IP");
        if (ipAddress == null) ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ipAddress == null) ipAddress = request.getRemoteAddr();
        return ipAddress;
    }

    private static String extractUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
} 