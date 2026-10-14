//package com.taskforge.service;
//
//import com.taskforge.dto.*;
//import com.taskforge.entity.User;
//import com.taskforge.repository.UserRepository;
//import com.taskforge.security.JwtService;
//import org.springframework.security.authentication.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
//                       AuthenticationManager authenticationManager, JwtService jwtService) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
//    }
//
//    public AuthResponse register(RegisterRequest request) {
//        if (userRepository.existsByEmailIgnoreCase(request.email())) {
//            throw new IllegalArgumentException("Email is already registered");
//        }
//
//        User user = new User();
//        user.setFullName(request.fullName().trim());
//        user.setEmail(request.email().trim().toLowerCase());
//        user.setPassword(passwordEncoder.encode(request.password()));
//        user.setRole("USER");
//
//        userRepository.save(user);
//
//        String token = jwtService.generateToken(user.getEmail(), user.getRole());
//        return new AuthResponse("Registration successful", token, user.getFullName(), user.getEmail(), user.getRole());
//    }
//
//    public AuthResponse login(LoginRequest request) {
//        authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(request.email().trim().toLowerCase(), request.password())
//        );
//
//        User user = userRepository.findByEmailIgnoreCase(request.email())
//            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
//
//        String token = jwtService.generateToken(user.getEmail(), user.getRole());
//        return new AuthResponse("Login successful", token, user.getFullName(), user.getEmail(), user.getRole());
//    }
//}


package com.taskforge.service;

import com.taskforge.dto.*;
import com.taskforge.entity.User;
import com.taskforge.repository.UserRepository;
import com.taskforge.security.JwtService;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CaptchaService captchaService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CaptchaService captchaService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.captchaService = captchaService;
    }

    public AuthResponse register(RegisterRequest request) {

        // CAPTCHA verification
        boolean captchaValid =
                captchaService.validateCaptcha(
                    request.captchaId(),
                    request.captchaAnswer()
                );

        if (!captchaValid) {
            throw new IllegalArgumentException(
                "Invalid or expired CAPTCHA"
            );
        }

        // Existing email validation
        if (userRepository.existsByEmailIgnoreCase(
                request.email())) {

            throw new IllegalArgumentException(
                "Email is already registered"
            );
        }

        // Existing user creation
        User user = new User();

        user.setFullName(
            request.fullName().trim()
        );

        user.setEmail(
            request.email().trim().toLowerCase()
        );

        user.setPassword(
            passwordEncoder.encode(
                request.password()
            )
        );

        user.setRole("USER");

        userRepository.save(user);

        // Existing JWT generation
        String token =
                jwtService.generateToken(
                    user.getEmail(),
                    user.getRole()
                );

        return new AuthResponse(
            "Registration successful",
            token,
            user.getFullName(),
            user.getEmail(),
            user.getRole()
        );
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email()
                    .trim()
                    .toLowerCase(),
                request.password()
            )
        );

        User user =
            userRepository
                .findByEmailIgnoreCase(
                    request.email()
                )
                .orElseThrow(
                    () -> new BadCredentialsException(
                        "Invalid email or password"
                    )
                );

        String token =
            jwtService.generateToken(
                user.getEmail(),
                user.getRole()
            );

        return new AuthResponse(
            "Login successful",
            token,
            user.getFullName(),
            user.getEmail(),
            user.getRole()
        );
    }
}