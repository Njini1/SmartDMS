# 🎓 SmartDMS – 사내 문서 통합 관리 및 승인 시스템
---

## 📑 목차
1. [프로젝트 개요](#-프로젝트-개요)
2. [기술 스택](#-기술-스택)
3. [문서 유형 및 설계 개요](#-문서-유형-및-설계-개요)
4. [주요 기능](#-주요-기능)
5. [현재 구현된 기능](#-현재-구현된-기능)
6. [ERD](#-erd)
7. [화면 구성](#-현재-구현된-화면-구성)
8. [문서 및 일정 관리](#-문서-및-일정-관리)
9. [테스트 환경](#-테스트-환경)

---

## 📌 프로젝트 개요

SmartDMS는 회사 내부에서 사용되는 문서를 관리하기 위한 **Spring Boot 기반 문서 통합 관리 시스템(DMS)** 

문서의 작성 → 버전 관리 → 동시성 제어 → 권한 관리 → 결재 → 폐기 관리까지 문서의 생애주기 전체를 관리하여 실제 업무에 필요한 기능을 제공  


> 초기에는 게시판(Board) 기반 CRUD 프로젝트로 시작하였으며, 현재는 이를 확장하여 **문서 관리 시스템(SmartDMS)** 을 개발 중입니다.

---

## 📖 기술 스택

| 분류           | 기술                                   |
|----------------|----------------------------------------|
| **Language**   | Java                                   |
| **Backend**    | Spring Boot 3.5.6                      |
| **Frontend**   | Thymeleaf, HTML/CSS/JS                 |
| **ORM**        | JPA                                    |
| **Database**   | MySQL, H2 (test)                       |
| **Build Tool** | Gradle                                 |
| **Others**     | Lombok, Spring Security                |

---

## 🧭 문서 유형 및 설계 개요

SmartDMS는 문서를 유형별로 분리하여 각 특성에 맞는 동시성 및 관리 전략을 적용
| 구분     | 웹 문서 (일반)       | 웹 문서 (결재)       | 파일 문서                |
| ------ | --------------- | --------------- | -------------------- |
| 작성 방식  | 웹 에디터           | 웹 에디터           | 파일 업로드               |
| 동시성 처리 | Optimistic Lock | Optimistic Lock | Check-in / Check-out |
| 버전 관리  | O               | O               | O                    |
| 결재 기능  | X               | O               |                   |
| 접근 권한  | O               | O               | O                    |

---

## 주요 기능
### ✅ 웹 문서 (일반 문서) -> 구현 진행 중

> 보고서, 회의록, 기술 문서 등 협업 중심 문서

- 웹 에디터 기반 문서 작성
- 문서 버전 관리
  - Document : 문서 메타데이터
  - DocumentVersion : 실제 문서 내용 및 변경 이력
- 현재 버전 포인터(currentVersion) 관리
- 변경 사유 기록(changeReason)
- 접근 권한 관리 (Read / Write / Admin)
- 문서 상태 관리
  - ACTIVE / ARCHIVED / DISPOSED
- 보존 기간 기반 폐기 관리
- 문서 히스토리

🔐 동시성 처리
- JPA @Version 기반 Optimistic Locking
- 동시에 수정 시 먼저 저장한 사용자만 반영
- 이후 저장 시 충돌 예외 발생으로 사용자에게 알림


### ✅ 웹 문서 (결재 문서)

> 기안서, 품의서, 요청서 등 전자결재 문서

- 일반 웹 문서 기능을 기반으로 결재 기능 확장
- 결재 상태 관리
  - DRAFT → IN_PROGRESS → APPROVED / REJECTED
- 결재 라인 관리
- 결재 이력 관리

🔒 결재 상태별 제약
- 결재 진행 중(IN_PROGRESS): 수정 제한
- 결재 완료(APPROVED): 읽기 전용
- 반려(REJECTED): 수정 후 재상신 가능 (새 버전 생성)


### ✅ 파일 문서 관리

> PDF, 엑셀, 한글 등 바이너리 문서

- 파일 업로드 기반 문서 관리
- **동시 편집 차단을 위한 Check-in / Check-out 방식**
- 체크아웃한 사용자만 수정 가능
- 체크인 시 새 파일 버전 생성
- 관리자 강제 체크인 기능 고려
---

## 🧭 현재 구현된 기능

### 📄 문서(Document)

- 문서 생성
- 문서 버전 관리 구조 설계
- 현재 버전 포인터 관리
- 문서 상태 관리 (ACTIVE / ARCHIVED / DISPOSED)
- Optimistic Lock 기반 동시성 제어
- 변경 사유 기록
- 문서 생성 시 툴바(toolbar) 적용

### 📰 문서 협업 게시판(Board)

- 게시글 목록 (페이징)
- 상세 조회
- 작성 / 수정 / 삭제 (작성자만 가능)
- 논리 삭제 (status = DELETED)
- 좋아요 (유저별 토글)

### 💬 댓글(Comment)

- 루트 댓글 + 대댓글(2 depth) 구조
- 삭제된 댓글 표시 처리
- @멘션 기능
- 작성자만 수정 / 삭제 가능

---

## 🧱 ERD
<img width="48%" alt="image" src="https://github.com/user-attachments/assets/0f8c92a2-c802-4121-9929-e295ac04322c" />

---

## 🖥️ 현재 구현된 화면 구성

#### <로그인/회원가입 및 문서 협업 게시판 목록>                                                                      
<div align="center">
  <img src="https://github.com/user-attachments/assets/d0b71fc2-65f9-496e-8f3b-e00e7d7cf2ef" width="48%" />
    <img src="https://github.com/user-attachments/assets/79d14d6b-887f-4964-b209-a1f3bfa6bc51" width="48%" />
</div>
<div></div>

#### <문서 협업 게시판 상세 및 댓글>
<div align="center">
  <img src="https://github.com/user-attachments/assets/93c1d64c-3ad1-4d69-b4a7-0b7f4d2950f2" width="48%" />
  <img src="https://github.com/user-attachments/assets/6bbf9d83-becb-4b93-9a04-45bdf4f32e35" width="48%" />
</div>
<div></div>

#### <문서 작성 및 문서 상세>
<div align="center">
  <img src="https://github.com/user-attachments/assets/c45e4761-cdbc-4a6a-8d46-00b624d9e108" width="48%" />
  <img src="https://github.com/user-attachments/assets/52a6f409-9a30-4b82-9a1d-e9e450a3005a" width="48%" />
</div>

#### <새 버전 문서 생성>
<div align="center">
  <img src="https://github.com/user-attachments/assets/6836970b-57a0-41dd-b40e-0cf468c2304d" width="48%" />
  <img src="https://github.com/user-attachments/assets/1aa73fdc-efff-4372-a70b-93e13c61528c" width="48%" />
</div>

---

## 🗂️ 문서 및 일정 관리

#### 📅 일정 관리  
🔗 https://www.notion.so/28ee550432f4800b9ccee04e883e022d?pvs=21  

#### ⚙️ 기능 구현 문서  
🔗 https://www.notion.so/28de550432f481449395f6ed1ac16442?pvs=21  

<div align="center">
  <img src="https://github.com/user-attachments/assets/ae6441cf-a14d-4aec-a572-7bd457702d4c" width="48%" />
  <img src="https://github.com/user-attachments/assets/6f0bdc31-51ee-4722-92f6-361f9c7745ea" width="48%" />
</div>

---

## 🧪 테스트 환경

### application.yml (테스트용 H2 환경)

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    username: sa
    password:
    url: jdbc:h2:mem:testdb
  application:
    name: eduboard
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create
    show-sql: true
    defer-datasource-initialization: true

server:
  servlet:
    session:
      timeout: 30m
```
