package com.example.sleep.app;

import com.example.sleep.prompts.CbtiWeekPrompts;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/cbti")
public class CbtiController {

  @GetMapping("/guide/{week}")
  public Map<String,Object> guide(@PathVariable int week) {
    return Map.of(
        "week", week,
        "guide", CbtiWeekPrompts.WEEK.getOrDefault(week, "")
    );
  }
}
