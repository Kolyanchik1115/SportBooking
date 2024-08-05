package com.application.SportBooking.service.user;

import com.application.bookstore.dto.user.google.UserGoogleRequestDto;
import com.application.bookstore.dto.user.google.UserGoogleResponseDto;
import com.application.bookstore.dto.user.registration.UserRegistrationRequestDto;
import com.application.bookstore.dto.user.registration.UserResponseDto;
import com.application.bookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserGoogleResponseDto google(UserGoogleRequestDto requestDto);
}
