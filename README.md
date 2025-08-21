# Sleep Counselor (Spring AI, RAG, Voice Bridge)

## 요구 환경변수
- `OPENAI_API_KEY`
- `PINECONE_API_KEY`, `PINECONE_ENVIRONMENT`, `PINECONE_INDEX_NAME`
- `BRIDGE_BASE_URL` (파이썬 STT/TTS 브리지, 기본: `http://localhost:8001`)

## 실행
```bash
mvn spring-boot:run
# 브라우저: http://localhost:8080/chat
