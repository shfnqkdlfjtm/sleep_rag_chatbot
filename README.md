# sleep_rag_chatbot
음성 기반 수면상담 RAG 챗봇


## Repository 구조

```plaintext
sleep-counselor-spring/
├─ pom.xml                 # Maven 프로젝트 설정 파일
├─ Dockerfile              # Docker 이미지 빌드 설정
├─ .dockerignore           # Docker 빌드시 제외할 파일 목록
├─ README.md               # 프로젝트 설명 문서
├─ src/main/java/com/example/sleep/
│  ├─ SleepCounselorApplication.java     # Spring Boot 메인 실행 클래스
│  ├─ config/             # 전역 설정
│  │  ├─ AppProperties.java
│  │  ├─ AiConfig.java
│  │  ├─ EnvCheckRunner.java
│  │  ├─ GlobalExceptionHandler.java
│  │  └─ WebConfig.java
│  ├─ app/                # 컨트롤러 (REST API)
│  │  ├─ UiController.java
│  │  ├─ ChatController.java
│  │  ├─ CbtiController.java
│  │  ├─ StatsController.java
│  │  └─ HealthController.java
│  ├─ conversation/       #대화 흐름 서비스
│  │  └─ ConversationService.java
│  ├─ prompts/            #프롬프트/시나리오 정의
│  │  ├─ Personas.java
│  │  └─ CbtiWeekPrompts.java
│  ├─ audio/              #음성 처리 (STT/TTS/감정)
│  │  ├─ SttAdapter.java
│  │  ├─ TtsAdapter.java
│  │  ├─ EmotionAdapter.java
│  │  └─ bridge/          # 외부 API 연동
│  │     ├─ ExternalSttClient.java
│  │     ├─ ExternalTtsClient.java
│  │     └─ ExternalEmotionClient.java
│  └─ services/           #핵심 서비스
│     ├─ ChatbotService.java     ← (RAG + LLM + 프롬프트 통합)
│     └─ dto/             # DTO (데이터 전송 객체)
│        ├─ ReferenceDoc.java
│        └─ ChatReply.java
└─ src/main/resources/
   ├─ application.yml      # 환경설정
   ├─ templates/chat.html  # 웹 UI 템플릿
   └─ static/              # 정적 리소스
      ├─ css/app.css
      └─ js/chat.js
