package com.application.sportbooking.security;

import com.application.sportbooking.dto.auth.login.UserLoginRequestDto;
import com.application.sportbooking.dto.auth.login.UserLoginResponseDto;
import com.application.sportbooking.exception.UnauthorizedException;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.email(),
                            requestDto.password())
            );

            String generatedToken = jwtUtil.generateToken(authentication.getName());

            User user = userRepository.findByEmail(requestDto.email())
                    .orElseThrow(() -> new UsernameNotFoundException(String
                            .format("Can't find user by email %s", requestDto.email()
                            )));
            return new UserLoginResponseDto(user, generatedToken);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid email or password");
        }
    }
}
