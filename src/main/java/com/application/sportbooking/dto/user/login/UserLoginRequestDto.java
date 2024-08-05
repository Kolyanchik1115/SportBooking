package com.application.sportbooking.dto.user.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotEmpty(message = "email should be not empty")
        @Email(message = "invalid email format")
        String email,
        @NotEmpty(message = "password should be not empty")
        @Length(min = 8, max = 35, message = "should be between 8 and 35 letters and symbols")
        String password
) {
}
