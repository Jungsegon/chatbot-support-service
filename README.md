# 📞 Chatbot Support System

이 프로젝트는 고객 지원을 위한 챗봇 기반 서비스로, Spring Boot를 기반으로 구축되었습니다.  
FAQ 답변, 사용자 요청 처리, 간단한 대화 로그 저장 기능을 제공합니다.

---

## 프로젝트 진행 사항

### 2025-01-23
- **프로젝트 초기 설정 완료**
    - Spring Boot 프로젝트 생성
    - Gradle 설정 완료
    - 기본 디렉토리 구조 설정

### 2025-01-24
- **회원가입 및 로그인 기능 추가**
    - 사용자 엔티티 및 서비스 구현
    - 로그인/회원가입 API 개발
    - 비밀번호 암호화 적용 (BCrypt)
- **Spring Security 적용**
    - 인증 및 인가 설정
    - 사용자 인증 필터 구현
- **JWT 인증 추가**
    - 로그인 시 JWT 토큰 발급
    - 요청 시 JWT 검증 및 사용자 인증

### 2025-02-05
- 프로필 api 추가, 회원가입 시 프로필 자동생성


## 🚀 프로젝트 개요

- **프로젝트명:** Chatbot Support System
- **기술 스택:**  
  - Backend: Spring Boot, Spring Security, JWT  
  - DB: MySQL  
  - Communication: REST API, WebSocket  
  - NLP: Dialogflow, Rasa (예정)  

---

## 📂 프로젝트 구조

## 🔑 주요 기능
 회원가입 및 로그인 (JWT 인증)
 고객 지원을 위한 챗봇 응답 처리
 WebSocket을 통한 실시간 메시징
 Spring Security 기반 인증 및 권한 부여
 NLP 엔진 연동 (Dialogflow, Rasa) (추가 예정)


## API 명세

