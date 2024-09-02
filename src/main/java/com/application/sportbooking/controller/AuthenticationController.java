package com.application.sportbooking.controller;

import com.application.sportbooking.dto.auth.login.UserLoginRequestDto;
import com.application.sportbooking.dto.auth.login.UserLoginResponseDto;
import com.application.sportbooking.dto.auth.registration.UserRegistrationRequestDto;
import com.application.sportbooking.dto.auth.registration.UserResponseDto;
import com.application.sportbooking.exception.RegistrationException;
import com.application.sportbooking.security.AuthenticationService;
import com.application.sportbooking.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Tag(name = "Authentication management", description = "Endpoints for authentication users")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @MutationMapping
    @Operation(summary = "Authorization", description = "User authorization")
    public UserLoginResponseDto login(@Argument("loginInput") @Valid
                                          UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @MutationMapping
    @Operation(summary = "Registration", description = "User registration")
    public UserResponseDto register(@Argument("registerInput") @Valid
                                        UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

}
