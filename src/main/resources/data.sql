-- 보험 관리 시스템 초기 테스트 데이터

-- 고객 데이터
INSERT INTO customer (name, email, phone, address, created_at, updated_at, created_by, updated_by) VALUES
('홍길동', 'hong@example.com', '010-1234-5678', '서울시 강남구 역삼동', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('김철수', 'kim@example.com', '010-2345-6789', '서울시 서초구 서초동', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('이영희', 'lee@example.com', '010-3456-7890', '서울시 마포구 합정동', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('박민수', 'park@example.com', '010-4567-8901', '부산시 해운대구 우동', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('최지영', 'choi@example.com', '010-5678-9012', '대구시 수성구 범어동', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');

-- 보험상품 데이터
INSERT INTO insurance_product (name, description, premium, coverage, created_at, updated_at, created_by, updated_by) VALUES
('자동차보험', '자동차 사고, 도난, 자연재해 보상', 500000.00, '사고, 도난, 자연재해, 화재', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('건강보험', '질병, 상해, 입원 치료비 보상', 300000.00, '질병, 상해, 입원, 수술', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('생명보험', '사망, 장애, 만기 보상', 1000000.00, '사망, 장애, 만기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('여행보험', '해외여행 중 사고, 질병, 여행 취소 보상', 50000.00, '사고, 질병, 여행취소, 짐분실', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('주택보험', '주택 화재, 도난, 자연재해 보상', 200000.00, '화재, 도난, 자연재해, 배상책임', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');

-- 계약 데이터
INSERT INTO contract (customer_name, product_name, start_date, end_date, created_at, updated_at, created_by, updated_by) VALUES
('홍길동', '자동차보험', '2024-01-01', '2024-12-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('김철수', '건강보험', '2024-02-01', '2025-01-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('이영희', '생명보험', '2024-03-01', '2029-02-28', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('박민수', '여행보험', '2024-06-01', '2024-06-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('최지영', '주택보험', '2024-01-15', '2024-12-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('홍길동', '건강보험', '2024-04-01', '2025-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('김철수', '자동차보험', '2024-05-01', '2025-04-30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');

-- 보안 감사 로그 샘플 데이터
INSERT INTO security_audit_log (username, action, resource, resource_id, ip_address, user_agent, session_id, request_method, request_url, request_params, response_status, execution_time, created_at) VALUES
('admin', 'LOGIN_SUCCESS', 'AUTHENTICATION', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'session123', 'POST', '/api/auth/login', 'username=admin', 200, 150, CURRENT_TIMESTAMP),
('admin', 'CREATE', 'CUSTOMER', '1', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'session123', 'POST', '/api/customers', 'name=홍길동&email=hong@example.com', 201, 200, CURRENT_TIMESTAMP),
('admin', 'READ', 'CUSTOMER', '1', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'session123', 'GET', '/api/customers/1', '', 200, 50, CURRENT_TIMESTAMP),
('admin', 'UPDATE', 'CUSTOMER', '1', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'session123', 'PUT', '/api/customers/1', 'phone=010-9999-8888', 200, 180, CURRENT_TIMESTAMP),
('admin', 'CREATE', 'INSURANCE_PRODUCT', '1', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 'session123', 'POST', '/api/insurance-products', 'name=자동차보험&premium=500000', 201, 250, CURRENT_TIMESTAMP); 