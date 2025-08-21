package com.example.sleep.audio;

import java.nio.file.Path;

public interface SttAdapter {
  String transcribe(Path audioPath) throws Exception;
}
