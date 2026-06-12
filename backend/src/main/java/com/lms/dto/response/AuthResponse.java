package com.lms.dto.response;

public record AuthResponse(String token, UserResponse user) {
}
