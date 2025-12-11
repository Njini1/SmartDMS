# 🎓 SmartDMS – 사내 문서 통합 관리 및 승인 시스템
---

## 📑 목차
1. [프로젝트 개요](#-프로젝트-개요)
2. [기술 스택](#-기술-스택)
3. [주요 기능](#-주요-기능)
4. [현재 구현된 기능](#-현재-구현된-기능)
6. [ERD](#-erd)
7. [화면 구성](#-화면-구성)
8. [문서 및 일정 관리](#-문서-및-일정-관리)
9. [테스트 환경](#-테스트-환경)

---

## 📌 프로젝트 개요

SmartDMS는 회사 내부에서 사용되는 문서를 관리하기 위한 **Spring Boot 기반 문서 통합 관리 시스템(DMS)** 

문서의 작성 → 버전 관리 → 동시성 제어 → 권한 관리 → 폐기 관리까지 문서의 생애주기 전체를 관리하여 실제 업무에 필요한 기능을 제공


> 현재는 로그인/게시글 CRUD 기반에서 **문서 버전 관리 기능**을 개발 중입니다.

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

## 🧭 주요 기능(구현 전)

### ✅ 문서 버전 관리 (Document Versioning) -> 구현 완료
문서의 모든 변경 이력을 추적하고 관리하여 이전 상태로 복원
- 버전 관리: 문서의 메타데이터를 담는 Document와 실제 내용을 담는 DocumentVersion을 1:N 관계로 분리하여 관리
- 새 버전 생성: 문서 수정 시 기존 버전을 덮어쓰지 않고, 새로운 DocumentVersion 데이터를 생성하고 Document의 현재 버전 포인터를 업데이트
- 이력 조회: 특정 문서의 전체 버전 이력을 조회하고, 과거 버전의 내용을 열람할 수 있는 기능을 제공(선택 사항) -> 구현 전

### ✅ 같은 문서를 여러 사람이 수정할 때 동시성 처리 (Optimistic Locking) -> 구현 완료
동일 문서를 여러 사용자가 동시에 수정할 때 충돌 방지
- 낙관적 잠금 (Optimistic Locking): Document 엔티티에 @Version 필드를 사용하여 동시성 충돌을 제어
  - 두 사용자가 동시에 문서를 수정하고 저장하려 할 때, 먼저 저장한 사용자의 버전만 반영하고, 이후 저장 시도하는 사용자에게는 **OptimisticLockingFailureException**을 발생시켜 충돌을 알림

### ✅ 버전 비교 (Diff) -> 구현 진행 중
문서의 두 버전 간의 변경 내용을 시각적으로 비교하여 표시
- 텍스트 비교 알고리즘: 두 DocumentVersion의 content 필드를 비교하여 변경된 내용(추가, 삭제, 수정)을 하이라이트하여 표시

### ✅ 문서 권한 관리
문서 단위로 접근 및 수정 권한을 관리하여 보안을 강화
- 권한 레벨: 문서별 읽기(Read), 쓰기(Write), 관리(Admin) 등의 권한을 사용자/부서 단위로 부여

### ✅ 문서 폐기 (보존기간/삭제) 관리
더 이상 사용되지 않는 문서를 안전하게 처리하고 관리
- 소프트 삭제 (Soft Delete): Document 엔티티에 status 필드를 두어 실제 레코드를 삭제하지 않고 상태만 변경
- 폐기 승인 플로우: 문서 폐기 요청 → 관리자 승인 → 폐기 완료(Disposed) 상태 변경
- 보존 기간: 폐기된 문서에 대해 보존 기간을 설정하고, 기간 만료 시 영구 삭제할 수 있는 관리 기능을 제공

### 🔹 추후 추가 사항
- 직원 간 채팅 기능: 커뮤니케이션을 위한 채팅 기능 구현
- 통합 검색 기능: 제목, 태그, 내용 기반의 강력한 검색 기능 구현

---

## 🧭 현재 구현된 기능

### 📰 게시글(Board)

- 게시글 목록(페이징)
- 상세 조회
- 작성/수정/삭제 (작성자만 가능)
- 논리 삭제(status = DELETED)
- 좋아요(유저별 토글)
- 댓글/대댓글 2depth 구조

### 💬 댓글(Comment)

- 루트 댓글 + 대댓글 트리 구조  
- 삭제된 댓글은 `"삭제된 댓글입니다."` 표시  
- 대댓글 작성 시 @멘션 기능  
- 작성자만 수정/삭제 가능

---

## 🧱 ERD
<img width="48%" alt="image" src="https://github.com/user-attachments/assets/0f8c92a2-c802-4121-9929-e295ac04322c" />

---

## 🖥️ 현재 구현된 화면 구성

#### <로그인/회원가입 및 게시물 목록>                                                                      
<div align="center">
  <img src="https://github.com/user-attachments/assets/d0b71fc2-65f9-496e-8f3b-e00e7d7cf2ef" width="48%" />
    <img src="https://github.com/user-attachments/assets/0345cb38-608a-43d2-9338-0d5d043bf1df" width="48%" />
</div>
<div></div>

#### <게시물 상세 및 댓글>
<div align="center">
  <img src="https://github.com/user-attachments/assets/93c1d64c-3ad1-4d69-b4a7-0b7f4d2950f2" width="48%" />
  <img src="https://github.com/user-attachments/assets/6bbf9d83-becb-4b93-9a04-45bdf4f32e35" width="48%" />
</div>
<div></div>

#### <새 버전 문서 생성>
<div align="center">
  <img src="https://github.com/user-attachments/assets/c7b312fb-dab3-4bd1-b5b4-5822eaa2842b" width="48%" />
  <img src="https://github.com/user-attachments/assets/88d58bd8-c865-4b35-9bc4-5848f54d9f79" width="48%" />
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
