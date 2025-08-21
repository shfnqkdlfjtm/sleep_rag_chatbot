package com.example.sleep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.pinecone.PineconeVectorStore;
import org.springframework.ai.pinecone.api.PineconeApi;
import org.springframework.ai.vectorstore.VectorStore;

@Configuration
public class AiConfig {

  @Bean
  public ChatClient chatClient(ChatModel chatModel) {
    return ChatClient.builder(chatModel).build();
  }

  @Bean
  public PineconeApi pineconeApi(AppProperties props) {
    return new PineconeApi(props.getPinecone().getApiKey(), props.getPinecone().getEnvironment());
  }

  @Bean
  public VectorStore vectorStore(PineconeApi pineconeApi, EmbeddingModel embeddingModel, AppProperties props) {
    return new PineconeVectorStore(pineconeApi, embeddingModel, props.getPinecone().getIndexName());
  }
}
