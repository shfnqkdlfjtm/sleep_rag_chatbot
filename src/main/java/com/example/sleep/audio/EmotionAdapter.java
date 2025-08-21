package com.example.sleep.audio;

import java.nio.file.Path;

public interface EmotionAdapter {
  String analyze(Path audioPath) throws Exception;
}
