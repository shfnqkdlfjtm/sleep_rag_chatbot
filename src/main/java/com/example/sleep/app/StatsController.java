ackage com.example.sleep.app;

import com.example.sleep.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {
  private final ConversationService conv;
  @GetMapping("/{userId}")
  public Map<String,Integer> get(@PathVariable String userId){
    return conv.getStats(userId);
  }
}