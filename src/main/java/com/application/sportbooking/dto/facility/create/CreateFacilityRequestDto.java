package com.application.sportbooking.dto.facility.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import com.application.sportbooking.model.Facility.SportType;
import com.application.sportbooking.model.Facility.CoveringType;
import com.application.sportbooking.model.Facility.FacilityType;
import java.util.Set;

@Data
public class CreateFacilityRequestDto {
    @NotNull(message = "name should be not empty")
    @Length(min = 4, message = "name can't be less than 4 letters")
    private String name;
    @NotNull(message = "name should be not empty")
    @Length(min = 5, message = "address can't be less than 4 letters")
    private String address;
    @NotNull(message = "sport type should be not empty")
    private Set<SportType> sportType;
    @NotNull(message = "covering type should be not empty")
    private CoveringType coveringType;
    @NotNull(message = "facility type should be not empty")
    private FacilityType facilityType;
    @Length(min = 4, message = "description can't be less than 4 letters")
    private String description;
    private String location;
    @Positive(message = "min booking time should be positive")
    private Integer minBookingTime;
}
