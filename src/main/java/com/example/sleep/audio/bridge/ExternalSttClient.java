package com.example.sleep.audio.bridge;

import com.example.sleep.audio.SttAdapter;
import com.example.sleep.config.AppProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ExternalSttClient implements SttAdapter {
  private final WebClient http;
  public ExternalSttClient(AppProperties props){
    this.http = WebClient.builder().baseUrl(props.getBridge().getBaseUrl()).build();
  }
  @Override
  public String transcribe(Path p) throws Exception {
    byte[] data = Files.readAllBytes(p);
    return http.post().uri("/stt")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .bodyValue(data)
        .retrieve().bodyToMono(String.class).block();
  }
}
