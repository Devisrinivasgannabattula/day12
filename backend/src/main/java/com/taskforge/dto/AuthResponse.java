package com.taskforge.dto;

public record AuthResponse(String message, String token, String fullName, String email, String role) {}
