package com.application.sportbooking.dto.auth.login;

import com.application.sportbooking.model.User;

public record UserLoginResponseDto(User user, String accessToken) {
}
