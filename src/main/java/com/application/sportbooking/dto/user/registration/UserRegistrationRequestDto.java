package com.application.sportbooking.dto.user.registration;

import com.application.sportbooking.util.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch
public class UserRegistrationRequestDto {
    @NotBlank(message = "email can't be empty")
    @Email(message = "invalid email format")
    private String email;
    @NotBlank(message = "password should be not empty")
    @Length(min = 8, max = 35, message = "should be between 8 and 35 letters and symbols")
    private String password;
    @NotBlank(message = "repeated password should be not empty")
    @Length(min = 8, max = 35, message = "should be between 8 and 35 letters and symbols")
    private String confirmPassword;
}
