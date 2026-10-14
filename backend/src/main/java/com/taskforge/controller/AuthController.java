//package com.taskforge.controller;
//
//import com.taskforge.dto.*;
//import com.taskforge.service.AuthService;
//import jakarta.validation.Valid;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
//    }
//
//    @PostMapping("/login")
//    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
//        return authService.login(request);
//    }
//}

package com.taskforge.controller;

import com.taskforge.dto.*;
import com.taskforge.service.AuthService;
import com.taskforge.service.CaptchaService;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    public AuthController(
            AuthService authService,
            CaptchaService captchaService) {

        this.authService = authService;
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captcha() {

        CaptchaService.CaptchaResponseData captcha =
                captchaService.createCaptcha();

        return ResponseEntity.ok(
            new CaptchaResponse(
                captcha.captchaId(),
                captcha.question()
            )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}