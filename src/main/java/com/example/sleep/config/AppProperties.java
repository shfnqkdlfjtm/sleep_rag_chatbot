package com.example.sleep.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private Pinecone pinecone = new Pinecone();
  private Tts tts = new Tts();
  private Bridge bridge = new Bridge();

  public Pinecone getPinecone() { return pinecone; }
  public Tts getTts() { return tts; }
  public Bridge getBridge() { return bridge; }

  public static class Pinecone {
    private String apiKey;
    private String environment;
    private String indexName;
    public String getApiKey(){return apiKey;} public void setApiKey(String v){apiKey=v;}
    public String getEnvironment(){return environment;} public void setEnvironment(String v){environment=v;}
    public String getIndexName(){return indexName;} public void setIndexName(String v){indexName=v;}
  }
  public static class Tts {
    private String engine="gtts";
    private String lang="ko";
    public String getEngine(){return engine;} public void setEngine(String v){engine=v;}
    public String getLang(){return lang;} public void setLang(String v){lang=v;}
  }
  public static class Bridge {
    private String baseUrl="http://localhost:8001";
    public String getBaseUrl(){return baseUrl;} public void setBaseUrl(String v){baseUrl=v;}
  }
}
