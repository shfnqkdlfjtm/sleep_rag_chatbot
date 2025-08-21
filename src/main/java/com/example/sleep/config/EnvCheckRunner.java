package com.example.sleep.config;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments; import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment; import org.springframework.stereotype.Component;

import java.util.List; import java.util.Objects; import java.util.stream.Collectors;

@Component
public class EnvCheckRunner implements ApplicationRunner {
  private static final Logger log = LoggerFactory.getLogger(EnvCheckRunner.class);
  private final Environment env;
  public EnvCheckRunner(Environment env){this.env=env;}

  @Override public void run(ApplicationArguments args) {
    List<String> req = List.of("OPENAI_API_KEY","PINECONE_API_KEY","PINECONE_ENVIRONMENT","PINECONE_INDEX_NAME");
    var missing = req.stream().filter(k -> Objects.isNull(System.getenv(k)) && Objects.isNull(env.getProperty(k))).collect(Collectors.toList());
    req.forEach(k -> log.info("{} {}: {}", missing.contains(k)?"✗":"✓", k, missing.contains(k)?"Missing":"Present"));
    if(!missing.isEmpty()) throw new IllegalStateException("Missing env: "+String.join(", ", missing));
  }
}
