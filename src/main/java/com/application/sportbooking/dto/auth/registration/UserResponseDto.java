package com.application.sportbooking.dto.auth.registration;

import com.application.sportbooking.model.User;

public record UserResponseDto(User user, String accessToken) {
}
