package com.core.common.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 암호화 유틸리티 클래스
 * 
 * 주요 기능:
 * - AES 암호화/복호화
 * - BCrypt 비밀번호 해싱
 * - 비밀번호 검증
 * 
 * @author core
 */
@Component
public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "YourSecretKey123"; // 실제 운영환경에서는 환경변수나 설정 파일에서 관라 -> application.properties, applicaition.yml 같은거?.

    /**
     * 문자열을 AES 알고리즘으로 암호화합니다.
     *
     * @param value 암호화할 문자열
     * @return 암호화된 문자열 (Base64 인코딩)
     * @throws RuntimeException 암호화 중 오류 발생 시
     */
    public String encrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting value", e);
        }
    }

    /**
     * 암호화된 문자열을 AES 알고리즘으로 복호화합니다.
     *
     * @param encrypted 복호화할 문자열 (Base64 인코딩)
     * @return 복호화된 문자열
     * @throws RuntimeException 복호화 중 오류 발생 시
     */
    public String decrypt(String encrypted) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting value", e);
        }
    }

    /**
     * 비밀번호를 BCrypt 알고리즘으로 해싱합니다.
     *
     * @param password 해싱할 비밀번호
     * @return 해싱된 비밀번호
     */
    public String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * 원본 비밀번호와 해싱된 비밀번호가 일치하는지 검증합니다.
     *
     * @param rawPassword 원본 비밀번호
     * @param encodedPassword 해싱된 비밀번호
     * @return 비밀번호 일치 여부
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }
} 