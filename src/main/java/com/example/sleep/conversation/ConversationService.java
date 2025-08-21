package com.example.sleep.conversation;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConversationService {

  public record Msg(String role, String content, LocalDateTime ts, String emotion) {}

  private final Map<String, List<Msg>> messages = new HashMap<>();
  private final Map<String, Map<String,Integer>> stats = new HashMap<>();

  public List<Msg> getHistory(String userId) {
    return messages.computeIfAbsent(userId, k -> new ArrayList<>());
  }

  public void append(String userId, Msg msg) {
    getHistory(userId).add(msg);
    // 간단 통계
    stats.computeIfAbsent(userId, k -> new HashMap<>(Map.of("total",0,"positive",0,"negative",0)))
         .merge("total", 1, Integer::sum);
  }

  public Map<String,Integer> getStats(String userId) {
    return stats.computeIfAbsent(userId, k -> new HashMap<>(Map.of("total",0,"positive",0,"negative",0)));
  }
}