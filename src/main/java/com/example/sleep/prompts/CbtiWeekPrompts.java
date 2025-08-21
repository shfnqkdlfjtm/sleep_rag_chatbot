package com.example.sleep.prompts;

import java.util.Map;

/** CBT-I 1~8주 가이드 (요약 버전) */
public final class CbtiWeekPrompts {
  private CbtiWeekPrompts(){}

  public static final Map<Integer,String> WEEK = Map.ofEntries(
    Map.entry(1, "[1주차] 수면일지 시작, 수면위생 기본 점검"),
    Map.entry(2, "[2주차] 수면제한/자극조절 도입, 침대=수면 규칙"),
    Map.entry(3, "[3주차] 생활/환경 수면위생 강화, 전자기기 절제"),
    Map.entry(4, "[4주차] 이완요법(복식호흡/명상/PMR) 연습"),
    Map.entry(5, "[5주차] 인지 재구성(비현실적 믿음 다루기)"),
    Map.entry(6, "[6주차] 중간 평가, 수면효율 기반 조정"),
    Map.entry(7, "[7주차] 유지/보완, 실천 강화"),
    Map.entry(8, "[8주차] 최종 평가, 재발 방지 계획 정리")
  );
}
