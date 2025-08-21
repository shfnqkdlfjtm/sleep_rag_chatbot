package com.example.sleep.services;

import com.example.sleep.prompts.CbtiWeekPrompts;
import com.example.sleep.prompts.Personas;
import com.example.sleep.services.dto.ChatReply;
import com.example.sleep.services.dto.ReferenceDoc;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatbotService {

  private final ChatClient chat;
  private final VectorStore vectorStore; // null 가능

  public ChatbotService(ChatClient chat, ObjectProvider<VectorStore> vsProvider) {
    this.chat = chat;
    this.vectorStore = vsProvider.getIfAvailable();
  }

  public ChatReply getResponse(String userInput, String personaName, Integer cbtiWeek) {
    String personaPrompt = Personas.PERSONAS.getOrDefault(
        personaName, Personas.PERSONAS.get("전문가 상담가")
    );

    // (옵션) 유사도 검색
    List<String> contexts = new ArrayList<>();
    List<ReferenceDoc> refs = new ArrayList<>();
    if (vectorStore != null) {
      var results = vectorStore.similaritySearch(SearchRequest.query(userInput).withTopK(5));
      results.forEach(doc -> {
        contexts.add(doc.getContent());
        var md = doc.getMetadata();
        refs.add(new ReferenceDoc(
            doc.getContent(),
            String.valueOf(md.getOrDefault("disease","")),
            String.valueOf(md.getOrDefault("tab",""))
        ));
      });
    }

    String contextBlock = contexts.isEmpty()? "관련 정보가 없습니다." : String.join("\n---\n", contexts);
    String weekText = (cbtiWeek != null) ? CbtiWeekPrompts.WEEK.getOrDefault(cbtiWeek,"") : "";

    String finalPrompt = """
        %s

        [참고 컨텍스트]
        %s

        %s

        사용자 메시지: %s

        지침:
        - 한국어로, 페르소나 톤을 유지하세요.
        - 컨텍스트에 근거해 간결/정확하게 답하세요.
        - 단계/목록은 번호로 정리하세요.
        """.formatted(personaPrompt, contextBlock, weekText, userInput);

    String answer = chat.prompt().user(finalPrompt).call().content();
    return new ChatReply(answer.trim(), refs);
  }
}
