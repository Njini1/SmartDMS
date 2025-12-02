# 🎓 SmartDMS – 사내 문서 통합 관리 및 승인 시스템

---

## 📑 목차
1. [프로젝트 목표](#-프로젝트-목표)
2. [주요 기능](#-주요-기능)
    - [사용자](#-사용자-user)
    - [게시물](#-게시물-board)
    - [댓글](#-댓글-comment)
    - [좋아요](#-좋아요-LikeBoard)
3. [화면](#-화면-구성)
4. [ERD](#-erd)
5. [기술 스택](#-기술-스택)
6. [문서](#-문서)
7. [테스트 환경](#-테스트-환경)
8. [추가 기능](#-추가-기능)
9. [향후 계획](#-향후-계획)

---

## 📌 프로젝트 목표

**사내 문서 통합 관리 및 승인 시스템**

기업 내부에서 생성되는 각종 문서(보고서, 기획서, 계약서, 회의록 등)를 안전하고 효율적으로 관리하기 위한 사내 문서 시스템을 구축한다.
문서의 버전 충돌, 외부 유출 위험, 검색 비효율 등 문제를 해소하고 팀·부서 단위의 협업 생산성과 보안성을 동시에 높인다.

> 현재는 게시글 CRUD 기능만 구현된 초기 단계이며, 앞으로 이를 확장하여 사내 문서 통합 관리 및 승인 시스템으로 발전시키는 것을 목표로 하고 있다.

---

## 🧭 주요 기능

### 🔐 사용자 (User)

- **로그인**
    - 세션 타임아웃(30분 미사용 시 만료)
    - 아이디 및 비밀번호 오류 시 알림 표시
- **회원가입**
    - 데이터 유효성 검사 수행
- **로그아웃**

---

### 📰 게시물 (Board)

#### ✅ 게시물 목록 보기
- 활성화된 게시물만 조회
- 페이징 처리
- 출력 항목: 제목 / 내용 / 작성자 / 좋아요 개수

#### ✅ 상세 게시물 보기
- 게시물 내용 및 좋아요 개수 표시
- 로그인 상태에 따라 다르게 동작:
    - **로그인한 사용자**: 좋아요 여부 확인 가능
    - **비로그인 사용자**: 좋아요 / 댓글 / 답글 클릭 시 “로그인 필요” 알림 표시
- 댓글 목록 출력

#### ✅ 게시물 작성
- 로그인한 사용자만 가능

#### ✅ 게시물 수정
- 로그인한 사용자 중 작성자만 수정 가능

#### ✅ 게시물 삭제
- 논리 삭제(`status = DELETED`)
- 로그인한 사용자 중 작성자만 삭제 가능

---

### 💬 댓글 (Comment)

#### ✅ 댓글 작성
- **부모 댓글**: 대댓글을 작성하고자 하는 대상 댓글  
  → `@작성자` 맨션용으로 사용
- **루트 댓글**: 댓글 목록 표시 시 루트 댓글 기준으로  
  루트 댓글과 대댓글을 **2단계 트리 구조**로 표시
- **저장 로직**
    - 루트 댓글: 부모 댓글 `NULL`, 루트 댓글은 자기 자신
    - 대댓글: 부모 댓글은 대상 댓글, 루트 댓글은 부모의 루트 댓글로 지정

#### ✅ 댓글 목록 보기
- **삭제된 댓글 포함 전체 표시**
    - 삭제된 댓글은 `"삭제된 댓글입니다."`로 표시
- 나머지 댓글은 `@작성자 + 내용` 형태로 표시

#### ✅ 댓글 삭제
- 논리 삭제(`status = DELETED`)
- 로그인한 사용자 중 작성자만 가능

#### ⚙️ 추후 고려 사항
- 자식 댓글 존재 시, 부모 댓글은 삭제 상태로만 변경
- 부모 댓글이 삭제 상태이며 자식 댓글이 모두 삭제된 경우 DB에서 실제 삭제

#### ✅ 댓글 수정
- 로그인한 사용자 중 작성자만 가능

#### 🧩 댓글 구조 예시
```댓글1(작성자A) - 댓글 내용
├─ 대댓글1-1(작성자B) - @작성자A 댓글 내용
│ └─ 대댓글1-2(작성자C) - @작성자B 댓글 내용
├─ 대댓글1-3(작성자D) - @작성자A 댓글 내용
댓글2(작성자E) - 댓글 내용
└─ 대댓글2-1(작성자F) - @작성자E 댓글 내용
```

### ❤️ 좋아요 (LikeBoard)

- **좋아요 토글 기능**
  - 처음 클릭 시 → 좋아요 테이블에 데이터 생성(`liked = true`)
  - 이후 클릭 시 → `liked = !liked` 로 상태 변경
- **좋아요 개수 표시**
- **유저별 좋아요 여부 확인**

---

## 🖥️ 화면 구성
<div align="center">
  <img src="https://github.com/user-attachments/assets/d0b71fc2-65f9-496e-8f3b-e00e7d7cf2ef" width="48%" alt="로그인" style="margin-right: 20px;" />
  <img src="https://github.com/user-attachments/assets/9c490880-504f-4a06-85e3-287e73222790" width="48%" alt="회원가입" />
</div>
<div align="center">
  <img src="https://github.com/user-attachments/assets/0345cb38-608a-43d2-9338-0d5d043bf1df" width="48%" alt="게시물목록" style="margin-right: 20px;" />
  <img src="https://github.com/user-attachments/assets/e6bcfcf3-5143-416a-9329-9ddfda4e0f97" width="48%" alt="게시물작성" />
</div>
<div align="center">
  <img src="https://github.com/user-attachments/assets/5ca655e9-951e-4281-8e3e-7ad502d7ef37" width="48%" alt="상세게시글" style="margin-right: 20px;" />
  <img src="https://github.com/user-attachments/assets/6bbf9d83-becb-4b93-9a04-45bdf4f32e35" width="48%" alt="댓글" />
</div>

---

## 🧱 ERD
<img src="https://github.com/user-attachments/assets/d48d41a0-ac97-4c0c-a5da-6c18cc069896" alt="erd" width="70%" />

---
## 📖 기술 스택

| 분류 | 기술 |
|------|------|
| **Language** | Java |
| **Framework** | Spring Boot |
| **View Template** | Thymeleaf |
| **ORM** | JPA (Hibernate) |
| **DB** | H2 (in-memory) |
| **Build Tool** | Gradle |
| **Others** | Lombok, Spring Security (세션 관리), Bootstrap 5 |

---

## 🗂️ 문서

### 📅 일정 관리
🔗 [Notion - 일정 관리](https://www.notion.so/28ee550432f4800b9ccee04e883e022d?pvs=21)

### ⚙️ 기능 구현 문서
🔗 [Notion - 기능 구현](https://www.notion.so/28de550432f481449395f6ed1ac16442?pvs=21)

<div align="center">
  <img src="https://github.com/user-attachments/assets/ae6441cf-a14d-4aec-a572-7bd457702d4c" width="48%" alt="일정" style="margin-right: 20px;" />
  <img src="https://github.com/user-attachments/assets/6f0bdc31-51ee-4722-92f6-361f9c7745ea" width="48%" alt="구현문서" />
</div>

---

## 🧪 테스트 환경

### application.yml
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
  tomcat:
    persistent-sessions: false
  servlet:
    session:
      timeout: 30m # 30분 미사용 시 세션 만료
```
---

## 🏁 향후 계획

- 학사관리 기능 확장 (과목, 성적, 공지사항 등)  
- 관리자(Admin) 페이지 추가  
- 사용자 프로필 페이지 구현  
- 비밀번호 찾기 및 이메일 인증 기능  
