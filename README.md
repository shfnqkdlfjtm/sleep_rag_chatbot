# Sleep Counselor (Spring AI, RAG, Voice Bridge)

## 요구 환경변수
- `OPENAI_API_KEY`
- `PINECONE_API_KEY`, `PINECONE_ENVIRONMENT`, `PINECONE_INDEX_NAME`
- `BRIDGE_BASE_URL` (파이썬 STT/TTS 브리지, 기본: `http://localhost:8001`)

## Repository 구조

```plaintext
sleep-counselor-spring/
├─ pom.xml                               # Maven 설정
├─ Dockerfile                            # Docker 빌드
├─ .dockerignore                         # Docker 제외 목록
├─ README.md                             # 프로젝트 설명
├─ src/main/java/com/example/sleep/
│  ├─ SleepCounselorApplication.java     # Spring Boot 메인
│  ├─ config/                            # 전역 설정
│  │  ├─ AppProperties.java
│  │  ├─ AiConfig.java
│  │  ├─ EnvCheckRunner.java
│  │  ├─ GlobalExceptionHandler.java
│  │  └─ WebConfig.java
│  ├─ app/                               #  REST 컨트롤러
│  │  ├─ UiController.java
│  │  ├─ ChatController.java             # 사용자 컨텍스트 포함 호출
│  │  ├─ CbtiController.java
│  │  ├─ StatsController.java
│  │  └─ HealthController.java
│  ├─ conversation/                      # 대화 흐름
│  │  └─ ConversationService.java
│  ├─ prompts/                           # 프롬프트 정의
│  │  ├─ Personas.java
│  │  └─ CbtiWeekPrompts.java
│  ├─ audio/                             # STT/TTS/감정
│  │  ├─ SttAdapter.java
│  │  ├─ TtsAdapter.java
│  │  ├─ EmotionAdapter.java
│  │  └─ bridge/
│  │     ├─ ExternalSttClient.java
│  │     ├─ ExternalTtsClient.java
│  │     └─ ExternalEmotionClient.java
│  ├─ domain/                            # 사용자/수면 도메인
│  │  ├─ User.java
│  │  └─ SleepRecord.java
│  ├─ repository/                        # JPA 리포지토리
│  │  ├─ UserRepository.java
│  │  └─ SleepRecordRepository.java
│  ├─ security/                          # 인증 유틸
│  │  └─ AuthUtils.java                  # (JWT 등에서 userId 추출)
│  └─ services/                          # 핵심 서비스
│     ├─ ChatbotService.java             # RAG + LLM + 프롬프트 + 사용자 컨텍스트
│     ├─ UserContextService.java         # 최근 30일 요약/패턴 조립
│     └─ dto/                            # DTO 모음
│        ├─ ChatReply.java
│        ├─ ReferenceDoc.java
│        ├─ UserContext.java             # LLM에 전달할 요약 컨텍스트
│        └─ SleepSummary.java            # 평균/비율/변동성 요약
└─ src/main/resources/
   ├─ application.yml                     # 환경설정
   ├─ templates/
   │  └─ chat.html                        # 웹 UI 템플릿
   └─ static/
      ├─ css/app.css
      └─ js/chat.js

### (옵션) Flyway 사용 시
### src/main/resources/db/migration/
### └─ V1__init.sql                        # users, sleep_records 테이블/인덱스



## 실행
```bash
mvn spring-boot:run
# 브라우저: http://localhost:8080/chat


