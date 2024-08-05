package com.application.sportbooking.service.user;

import com.application.sportbooking.dto.user.registration.UserRegistrationRequestDto;
import com.application.sportbooking.dto.user.registration.UserResponseDto;
import com.application.sportbooking.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
