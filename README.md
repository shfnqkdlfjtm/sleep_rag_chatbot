# sleep_rag_chatbot
음성 기반 수면상담 RAG 챗봇


## Repository 구조

sleepcounselor_chatbot_spring/
├─ pom.xml
├─ Dockerfile
├─ .dockerignore
├─ README.md
├─ src/main/java/com/example/sleep/
│  ├─ SleepCounselorApplication.java
│  ├─ config/
│  │  ├─ AppProperties.java
│  │  ├─ AiConfig.java
│  │  ├─ EnvCheckRunner.java
│  │  ├─ GlobalExceptionHandler.java
│  │  └─ WebConfig.java
│  ├─ app/
│  │  ├─ UiController.java
│  │  ├─ ChatController.java
│  │  ├─ CbtiController.java
│  │  ├─ StatsController.java
│  │  └─ HealthController.java
│  ├─ conversation/
│  │  └─ ConversationService.java
│  ├─ prompts/
│  │  ├─ Personas.java
│  │  └─ CbtiWeekPrompts.java
│  ├─ audio/
│  │  ├─ SttAdapter.java
│  │  ├─ TtsAdapter.java
│  │  ├─ EmotionAdapter.java
│  │  └─ bridge/
│  │     ├─ ExternalSttClient.java
│  │     ├─ ExternalTtsClient.java
│  │     └─ ExternalEmotionClient.java
│  └─ services/
│     ├─ ChatbotService.java     ← (RAG+LLM+프롬프트 통합, 단일 서비스)
│     └─ dto/
│        ├─ ReferenceDoc.java
│        └─ ChatReply.java
└─ src/main/resources/
   ├─ application.yml
   ├─ templates/chat.html
   └─ static/
      ├─ css/app.css
      └─ js/chat.js
