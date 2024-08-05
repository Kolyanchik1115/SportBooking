package com.application.sportbooking.dto.user.registration;

import com.application.sportbooking.model.User;

public record UserResponseDto(User user, String accessToken) {
}
