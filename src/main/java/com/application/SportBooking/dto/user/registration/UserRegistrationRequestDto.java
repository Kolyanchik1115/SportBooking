package com.application.SportBooking.dto.user.registration;

import com.application.bookstore.util.validation.FieldMatch;
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
    private String repeatPassword;
    @NotBlank(message = "firstname should be not empty")
    @Length(min = 2, max = 35, message = "should be between 2 and 35 letters")
    private String firstName;
    @NotBlank(message = "lastname should be not empty")
    @Length(min = 2, max = 35, message = "should be between 2 and 35 letters")
    private String lastName;
    private String deliveryAddress;
}
