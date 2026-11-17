# omgo

렌주 규칙 기반의 실시간 웹 오목 서비스입니다.

## 기술 스택

- Backend: Java 21, Spring Boot, Spring Data JPA, WebSocket
- Frontend: React, TypeScript, Vite
- Database: PostgreSQL 17 (Docker)

## 사전 준비

- JDK 21
- Node.js (LTS 권장)
- Docker / Docker Compose

## 환경 변수

프로젝트 루트에 `.env` 파일을 만들고 아래 값을 채웁니다. `.env`는 Git에 올리지 않습니다.

| 키 | 설명 |
|---|---|
| `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` | PostgreSQL 컨테이너 생성 정보이자, 로컬에서 실행한 Spring의 DB 접속 정보 |
| `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` | Docker Compose로 `app` 컨테이너를 실행할 때의 DB 접속 정보 |
| `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET` | Google OAuth 클라이언트 정보 |
| `FRONTEND_URL` | 로컬 프론트엔드 주소 (현재 코드에서는 사용하지 않음) |

## 실행 방법

### 1. 데이터베이스

```bash
docker compose up -d db
```

PostgreSQL이 `localhost:5432`에서 실행됩니다.

### 2. 백엔드

```bash
./gradlew bootRun
```

Spring 서버가 `localhost:8080`에서 실행됩니다.
백엔드까지 Docker로 실행하려면 `docker compose up -d --build`를 사용합니다.

### 3. 프론트엔드

```bash
cd frontend
npm install
npm run dev
```

`http://localhost:5173`에서 확인합니다. 자세한 내용은 [frontend/README.md](frontend/README.md)를 참고합니다.

## 테스트

```bash
./gradlew test
```

`UserRepositoryTest` 등 일부 테스트는 실제 PostgreSQL을 사용하므로, 먼저 `docker compose up -d db`로 DB를 실행해야 합니다.

## 프로젝트 구조

```
src/main/java/io/github/dorgyuk/omgo/
├── global/      여러 도메인이 함께 쓰는 코드
│   ├── config/      공통 설정 (비밀번호 인코더, CORS 등)
│   └── exception/   공통 예외 처리 (ErrorCode, AppException, 전역 핸들러)
├── auth/        인증 (/api/auth/*: 가입·로그인·토큰, google/ 하위에 Google 로그인)
├── user/        사용자 엔티티와 생성·조회
└── game/        오목 규칙 엔진
frontend/        React 프론트엔드
```

## 개발 규칙

- **API 명세 우선**: 엔드포인트는 팀 Notion의 API 명세를 먼저 작성·확정한 뒤 구현합니다. 코드와 명세가 다르면 임의로 맞추지 않고 공유합니다.
- **오류 응답 형식**: `{ "errorCode": "...", "message": "..." }`
- **예외 처리**: `throw new AppException(ErrorCode.XXX)`를 기본으로 사용합니다.
  - 새 오류 코드는 `ErrorCode`의 해당 도메인 구역에 추가합니다.
  - 별도 예외 클래스는 그 예외를 따로 `catch`해야 하거나, 응답에 추가 데이터가 필요할 때만 만듭니다.
