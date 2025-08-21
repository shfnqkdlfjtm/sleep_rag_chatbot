package com.example.sleep.services.dto;

import java.util.List;

public record ChatReply(String answer, List<ReferenceDoc> references) {}
