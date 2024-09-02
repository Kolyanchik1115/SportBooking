package com.application.sportbooking.dto.profile.update;

import java.time.LocalDate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateProfileRequestDto {
    private LocalDate dateOfBirth;
    @Length(min = 4, message = "can't be less than 4 letters")
    private String fullName;
}
