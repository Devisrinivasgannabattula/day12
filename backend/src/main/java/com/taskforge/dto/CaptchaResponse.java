package com.taskforge.dto;

public record CaptchaResponse(
    String captchaId,
    String question
) {}