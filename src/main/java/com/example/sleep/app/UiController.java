package com.example.sleep.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
  @GetMapping({"/","/chat"})
  public String chat(){ return "chat"; }
}