package com.example.sleep.app;

import com.example.sleep.audio.SttAdapter;
import com.example.sleep.audio.TtsAdapter;
import com.example.sleep.conversation.ConversationService;
import com.example.sleep.services.ChatbotService;
import com.example.sleep.services.dto.ChatReply;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatbotService chat;
  private final ConversationService conv;
  private final SttAdapter stt;
  private final TtsAdapter tts;

  @PostMapping("/text")
  public Mono<Map<String,Object>> text(@RequestParam String userId,
                                       @RequestParam String persona,
                                       @RequestParam(required=false) Integer cbtiWeek,
                                       @RequestParam(defaultValue="false") boolean ttsEnabled,
                                       @RequestBody Map<String,String> body) {
    return Mono.fromCallable(() -> {
      String query = body.getOrDefault("query","");
      ChatReply reply = chat.getResponse(query, persona, cbtiWeek);

      conv.append(userId, new ConversationService.Msg("user", query, LocalDateTime.now(),"Neutral"));
      conv.append(userId, new ConversationService.Msg("assistant", reply.answer(), LocalDateTime.now(), null));

      String ttsBase64 = null;
      if (ttsEnabled) ttsBase64 = Base64.getEncoder().encodeToString(tts.synthesize(reply.answer(),"ko"));

      return Map.of(
          "answer", reply.answer(),
          "references", reply.references(),
          "ttsBase64", ttsBase64,
          "stats", conv.getStats(userId)
      );
    });
  }

  @PostMapping(value="/voice", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
  public Mono<Map<String,Object>> voice(@RequestParam String userId,
                                        @RequestParam String persona,
                                        @RequestParam(required=false) Integer cbtiWeek,
                                        @RequestParam(defaultValue="false") boolean ttsEnabled,
                                        @RequestPart("audio") FilePart audio) {
    Path tmp = Path.of(System.getProperty("java.io.tmpdir"), "in-"+System.nanoTime()+".webm");
    return audio.transferTo(tmp.toFile()).then(Mono.fromCallable(() -> {
      String transcript = stt.transcribe(tmp);
      ChatReply reply = chat.getResponse(transcript, persona, cbtiWeek);

      conv.append(userId, new ConversationService.Msg("user", transcript, LocalDateTime.now(),"Neutral"));
      conv.append(userId, new ConversationService.Msg("assistant", reply.answer(), LocalDateTime.now(), null));

      String ttsBase64 = null;
      if (ttsEnabled) ttsBase64 = Base64.getEncoder().encodeToString(tts.synthesize(reply.answer(),"ko"));
      Files.deleteIfExists(tmp);

      return Map.of(
          "transcript", transcript,
          "answer", reply.answer(),
          "references", reply.references(),
          "ttsBase64", ttsBase64,
          "stats", conv.getStats(userId)
      );
    }));
  }
}