# 📞 Chatbot Support System
이 프로젝트는 WebSocket을 활용하여 실시간 챗봇 응답 기능과 1:1 실시간 채팅 시스템을 구현하는 것을 목표로 합니다. 
사용자는 챗봇과 상호 작용하여 기본적인 문의를 해결할 수 있으며, 필요할 경우 상담원과 직접 채팅할 수 있습니다. 
또한, CI/CD(Continuous Integration & Continuous Deployment) 파이프라인을 구축하여 안정적인 배포 환경을 마련하는 것을 최종 목표로 합니다.
이 프로젝트는 고객 지원을 위한 챗봇 기반 서비스로, Spring Boot를 기반으로 구축되었습니다.  
FAQ 답변, 사용자 요청 처리, 간단한 대화 로그 저장 기능을 제공합니다.

---
## 개발일지
https://summer-hall-34b.notion.site/177fce9e338580f1a30aedb7df1d1a1d

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

### 2025-02-03
- 프로필 api 추가, 회원가입 시 프로필 자동생성

### 2025-02-04
- WebSocket 시작

### 2025-02-09
- WebSocket 1대1 채팅 구현완료.

### 2024-02-15
- 웹페이지 만들기 시작

### 2024-02-16~2024-02-17
- 채팅방 UI 구성, 관리자가 로그인하면 채팅방 목록 조회, 입장 시 1대1 채팅
- 사용자가 로그인하게 되면 바로 관리자와 1대1 채팅방화면이 뜸.

### 2024-02-18~2024-02-19
- 프론트엔드 개선 & WebSocket 연동 완료
- WebSocket 기반 채팅 기능 연동
- 사용자가 로그인하면 자동으로 admin과 1:1 채팅 연결
- 기존 메시지 불러오기 (/chat/messages/{roomId} API 호출)
- WebSocket을 통해 실시간 메시지 송수신
- WebSocket 연결이 끊어지면 자동 재연결

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
1. **회원 관리 및 인증**
    - Spring Security 기반 회원 인증 및 관리
    - 사용자 계정 조회 및 권한 관리
    - 프로필 관리
2. **WebSocket 기반 실시간 채팅**
    - 웹소켓 핸들러를 활용한 실시간 메시지 송수신
    - 고객과 상담원 간 **1:1 WebSocket 채팅**
3. **챗봇 응답 기능**
    - 간단한 FAQ 응답 처리
    - 자연어 처리(NLP) 기반 자동 응답 기능 (추가 개발 예정)
4. **채팅 기록 저장 및 조회**
    - 메시지 저장 기능 구현
    - 고객-상담원 채팅 기록 조회
5. **CI/CD**
    - 지속적으로 통합/배포 구현

## ERD 다이어그램
![img.png](img.png)

## API 명세

