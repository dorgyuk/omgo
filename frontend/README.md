# omgo frontend

omgo의 React + TypeScript + Vite 프론트엔드입니다.
프로젝트 전체 실행 방법은 [루트 README](../README.md)를 참고합니다.

## 명령어

```bash
npm install      # 의존성 설치
npm run dev      # 개발 서버 (http://localhost:5173)
npm run build    # 타입 검사 후 프로덕션 빌드 (dist/)
npm run lint     # ESLint
npm run preview  # 빌드 결과 미리보기
```

## API 요청과 개발 서버 프록시

- API는 `fetch("/api/...")`처럼 **상대 경로**로 호출합니다.
- 개발 서버는 `/api` 요청을 `http://localhost:8080`(Spring)으로 전달합니다. 설정은 `vite.config.ts`에 있습니다.
- 따라서 `npm run dev` 전에 **Spring 서버가 8080에서 실행 중**이어야 합니다. 꺼져 있으면 API 요청이 프록시 오류로 실패합니다.
- 프록시는 개발 서버에서만 동작하며, 빌드 결과물에는 포함되지 않습니다.

## 폴더 구조

```
src/
├── api/          백엔드 API 호출 함수
├── components/   여러 화면에서 쓰는 컴포넌트
├── pages/        라우트별 화면 (컴포넌트와 같은 이름의 CSS를 함께 둠)
├── App.tsx       라우팅
└── main.tsx      진입점
```
