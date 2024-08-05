package com.application.SportBooking.security;

import com.application.bookstore.dto.user.login.UserLoginRequestDto;
import com.application.bookstore.dto.user.login.UserLoginResponseDto;
import com.application.bookstore.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.email(),
                            requestDto.password())
            );

            String generatedToken = jwtUtil.generateToken(authentication.getName());
            return new UserLoginResponseDto(generatedToken);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid email or password");
        }
    }
}
