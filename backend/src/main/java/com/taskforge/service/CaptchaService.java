package com.taskforge.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CaptchaService {

    private static final long CAPTCHA_EXPIRY_SECONDS = 300;

    private final Map<String, CaptchaChallenge> challenges =
            new ConcurrentHashMap<>();

    public CaptchaResponseData createCaptcha() {

        int first = ThreadLocalRandom.current().nextInt(1, 10);
        int second = ThreadLocalRandom.current().nextInt(1, 10);

        String captchaId = UUID.randomUUID().toString();

        int answer = first + second;

        challenges.put(
            captchaId,
            new CaptchaChallenge(
                answer,
                Instant.now().plusSeconds(CAPTCHA_EXPIRY_SECONDS)
            )
        );

        return new CaptchaResponseData(
            captchaId,
            first + " + " + second + " = ?"
        );
    }

    public boolean validateCaptcha(
            String captchaId,
            String captchaAnswer) {

        if (captchaId == null || captchaAnswer == null) {
            return false;
        }

        CaptchaChallenge challenge =
                challenges.get(captchaId);

        if (challenge == null) {
            return false;
        }

        // CAPTCHA expired
        if (Instant.now().isAfter(challenge.expiresAt())) {
            challenges.remove(captchaId);
            return false;
        }

        boolean valid;

        try {
            valid =
                challenge.answer()
                == Integer.parseInt(captchaAnswer.trim());

        } catch (NumberFormatException ex) {
            valid = false;
        }

        // CAPTCHA can only be used once
        if (valid) {
            challenges.remove(captchaId);
        }

        return valid;
    }

    private record CaptchaChallenge(
        int answer,
        Instant expiresAt
    ) {}

    public record CaptchaResponseData(
        String captchaId,
        String question
    ) {}
}