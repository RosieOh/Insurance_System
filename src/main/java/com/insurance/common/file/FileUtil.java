package com.core.common.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 처리 유틸리티 클래스
 * 
 * 주요 기능:
 * - 파일 업로드
 * - 파일 삭제
 * - 파일 조회
 * - 파일 유효성 검증
 * 
 * @author core
 */
@Component
public class FileUtil {

    private static final String UPLOAD_DIR = "uploads";

    /**
     * 파일을 저장합니다.
     * - UUID를 사용하여 고유한 파일명 생성
     * - 업로드 디렉토리가 없으면 생성
     *
     * @param file 저장할 파일
     * @return 저장된 파일명
     * @throws IOException 파일 저장 중 오류 발생 시
     */
    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String newFilename = UUID.randomUUID().toString() + extension;
        
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath);
        
        return newFilename;
    }

    /**
     * 파일을 삭제합니다.
     *
     * @param filename 삭제할 파일명
     * @throws IOException 파일 삭제 중 오류 발생 시
     */
    public void deleteFile(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        Files.deleteIfExists(filePath);
    }

    /**
     * 파일을 조회합니다.
     *
     * @param filename 조회할 파일명
     * @return File 객체
     */
    public File getFile(String filename) {
        return new File(UPLOAD_DIR, filename);
    }

    /**
     * 파일의 확장자가 허용된 타입인지 검증합니다.
     *
     * @param file 검증할 파일
     * @param allowedTypes 허용된 파일 확장자 배열
     * @return 파일 타입 유효성 여부
     */
    public boolean isValidFileType(MultipartFile file, String[] allowedTypes) {
        String extension = getFileExtension(file.getOriginalFilename());
        for (String type : allowedTypes) {
            if (extension.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 파일의 크기가 최대 크기를 초과하지 않는지 검증합니다.
     *
     * @param file 검증할 파일
     * @param maxSize 최대 파일 크기 (바이트)
     * @return 파일 크기 유효성 여부
     */
    public boolean isValidFileSize(MultipartFile file, long maxSize) {
        return file.getSize() <= maxSize;
    }

    /**
     * 파일명에서 확장자를 추출합니다.
     *
     * @param filename 파일명
     * @return 파일 확장자
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
} 