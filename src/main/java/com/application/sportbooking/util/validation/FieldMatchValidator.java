package com.application.sportbooking.util.validation;

import com.application.sportbooking.dto.user.registration.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(requestDto.getPassword(), requestDto.getConfirmPassword());
    }
}
