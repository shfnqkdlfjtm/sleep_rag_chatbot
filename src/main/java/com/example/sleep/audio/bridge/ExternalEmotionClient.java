package com.example.sleep.audio.bridge;

import com.example.sleep.audio.EmotionAdapter;
import com.example.sleep.config.AppProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ExternalEmotionClient implements EmotionAdapter {
  private final WebClient http;
  public ExternalEmotionClient(AppProperties props){
    this.http = WebClient.builder().baseUrl(props.getBridge().getBaseUrl()).build();
  }
  @Override
  public String analyze(Path audioPath) throws Exception {
    byte[] data = Files.readAllBytes(audioPath);
    return http.post().uri("/emotion")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .bodyValue(data)
        .retrieve().bodyToMono(String.class).block();
  }
}
