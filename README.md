# spring-chatting-app

STOMP 기반 WebSocket 통신을 이용한 **Spring Boot 실시간 채팅 프로그램**입니다.  
Spring Security를 적용하여 인증된 사용자만 채팅에 참여할 수 있도록 구성하였으며,  
사용자 권한에 따라 채팅방 접근 범위를 분리했습니다.

---

## 기술 스택

### Backend

- **Java:** 17  
- **Spring Boot:** 3.5.5  
- **Build Tool:** Gradle  
- **ORM:** Spring Data JPA  
- **Security:** Spring Security  

### Database

- **MySQL**

### Real-time Communication

- **WebSocket**
- **STOMP**

### Logging

- **P6Spy** – SQL 로그 확인

### API Documentation

- **Swagger (SpringDoc OpenAPI)**

---

## 주요 기능

### 인증 및 권한

- Spring Security 기반 로그인
- 인증된 사용자만 채팅 기능 이용 가능
- 사용자 권한 분리
  - **consultant 사용자**
    - 모든 채팅방 조회 가능
  - **일반 사용자**
    - 본인의 채팅방만 조회 가능

---

### 채팅 기능 흐름

1. 사용자 로그인
2. 채팅방 목록 HTML 렌더링
3. 채팅방 개설
4. 채팅 송수신
   - STOMP 기반 실시간 메시지 전송
   - 메시지는 `StompChatController`로 전달
   - 컨트롤러에서 채팅 내용을 DB에 저장
   - 저장된 채팅 내용을 클라이언트로 반환
   - 실시간으로 채팅 화면 렌더링
5. 로그아웃 이후
   - DB에 저장된 채팅 내역 조회
   - 기존 채팅 내용 화면 렌더링 가능

---

## 아키텍처 특징

- STOMP 프로토콜을 이용한 양방향 실시간 통신
- WebSocket + REST 혼합 구조
- 채팅 메시지 영속화로 로그아웃 이후에도 대화 내역 유지
- 역할(Role) 기반 채팅방 접근 제어

---
