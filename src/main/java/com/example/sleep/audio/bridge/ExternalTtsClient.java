package com.example.sleep.audio.bridge;

import com.example.sleep.audio.TtsAdapter;
import com.example.sleep.config.AppProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExternalTtsClient implements TtsAdapter {
  private final WebClient http; private final String defaultLang;
  public ExternalTtsClient(AppProperties props){
    this.http = WebClient.builder().baseUrl(props.getBridge().getBaseUrl()).build();
    this.defaultLang = props.getTts().getLang();
  }
  @Override
  public byte[] synthesize(String text, String lang) {
    String use = (lang==null || lang.isBlank()) ? defaultLang : lang;
    return http.post().uri(uri->uri.path("/tts").queryParam("lang", use).build())
        .contentType(MediaType.TEXT_PLAIN)
        .bodyValue(text)
        .retrieve().bodyToMono(byte[].class).block();
  }
}
