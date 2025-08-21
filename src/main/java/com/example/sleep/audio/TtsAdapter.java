package com.example.sleep.audio;

public interface TtsAdapter {
  /** TTS 결과 mp3 bytes 반환 */
  byte[] synthesize(String text, String lang) throws Exception;
}
