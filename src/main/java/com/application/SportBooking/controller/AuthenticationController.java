package com.application.SportBooking.controller;

import com.application.SportBooking.dto.user.login.UserLoginResponseDto;
import com.application.SportBooking.security.AuthenticationService;
import com.application.SportBooking.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Tag(name = "Authentication management", description = "Endpoints for authentication users")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @MutationMapping
    @Operation(summary = "Authorization", description = "User authorization")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping(value = "/registration")
    @Operation(summary = "Registration", description = "User registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping(value = "/google")
    @Operation(summary = "Google authorization", description = "User google authorization")
    public UserGoogleResponseDto google(@RequestBody @Valid UserGoogleRequestDto requestDto) {
        return userService.google(requestDto);
    }
}
