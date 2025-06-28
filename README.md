# Insurance Management System

보험 관리 시스템으로, HashMap 형태의 데이터를 처리할 수 있는 공통 로직을 제공합니다.

## 주요 기능

### 1. HashMap 데이터 처리
- 모든 엔티티(Contract, Customer, InsuranceProduct)에 대해 HashMap으로 데이터를 받아 처리
- 유연한 데이터 구조 지원
- 타입 안전한 변환 로직

### 2. 공통 API 엔드포인트
- `/api/map-data/{entityType}` - HashMap으로 엔티티 생성/조회
- `/api/map-data/{entityType}/{id}` - HashMap으로 엔티티 수정/삭제

## 사용 예시

### 1. 고객 생성 (HashMap 사용)
```bash
POST /api/map-data/customer
Content-Type: application/json

{
  "data": {
    "name": "홍길동",
    "email": "hong@example.com",
    "phone": "010-1234-5678",
    "address": "서울시 강남구"
  },
  "requestType": "customer"
}
```

### 2. 계약 생성 (HashMap 사용)
```bash
POST /api/map-data/contract
Content-Type: application/json

{
  "data": {
    "customerName": "홍길동",
    "productName": "자동차보험",
    "startDate": "2024-01-01",
    "endDate": "2024-12-31"
  },
  "requestType": "contract"
}
```

### 3. 보험상품 생성 (HashMap 사용)
```bash
POST /api/map-data/insuranceproduct
Content-Type: application/json

{
  "data": {
    "name": "자동차보험",
    "description": "자동차 사고 보상",
    "premium": 500000.00,
    "coverage": "사고, 도난, 자연재해"
  },
  "requestType": "insuranceproduct"
}
```

### 4. 엔티티 조회
```bash
# 모든 고객 조회
GET /api/map-data/customer

# 특정 고객 조회
GET /api/map-data/customer/1
```

### 5. 엔티티 수정
```bash
PUT /api/map-data/customer/1
Content-Type: application/json

{
  "data": {
    "name": "홍길동",
    "email": "hong.updated@example.com",
    "phone": "010-9876-5432",
    "address": "서울시 서초구"
  }
}
```

### 6. 엔티티 삭제
```bash
DELETE /api/map-data/customer/1
```

## 기존 API와의 호환성

기존의 엔티티 기반 API도 그대로 사용 가능합니다:

```bash
# 기존 방식
POST /api/customers
Content-Type: application/json

{
  "name": "홍길동",
  "email": "hong@example.com",
  "phone": "010-1234-5678",
  "address": "서울시 강남구"
}
```

## 프로젝트 구조

```
src/main/java/com/insurance/
├── common/
│   ├── dto/
│   │   └── MapDataRequest.java          # HashMap 데이터 요청 DTO
│   ├── util/
│   │   └── MapDataConverter.java        # HashMap-엔티티 변환 유틸리티
│   └── controller/
│       └── MapDataController.java       # HashMap 데이터 처리 공통 컨트롤러
├── domain/
│   ├── contract/
│   ├── customer/
│   └── insuranceProduct/
└── InsuranceApplication.java
```

## 실행 방법

1. 프로젝트 빌드:
```bash
./gradlew build
```

2. 애플리케이션 실행:
```bash
./gradlew bootRun
```

3. H2 데이터베이스 콘솔 접속:
   - URL: http://localhost:8080/h2-console
   - JDBC URL: jdbc:h2:mem:testdb
   - Username: sa
   - Password: (비어있음)

## 주요 특징

1. **유연한 데이터 구조**: HashMap을 사용하여 다양한 형태의 데이터를 받을 수 있습니다.
2. **타입 안전성**: MapDataConverter를 통해 안전한 타입 변환을 제공합니다.
3. **확장성**: 새로운 엔티티 타입을 쉽게 추가할 수 있습니다.
4. **기존 API 호환성**: 기존 엔티티 기반 API와 함께 사용 가능합니다.
5. **공통 응답 형식**: 모든 API 응답이 일관된 형식으로 반환됩니다. 