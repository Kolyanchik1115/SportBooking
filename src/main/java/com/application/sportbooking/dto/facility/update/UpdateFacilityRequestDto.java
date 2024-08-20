package com.application.sportbooking.dto.facility.update;

import com.application.sportbooking.model.Facility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateFacilityRequestDto {

    @Positive(message = "id cannot be negative")
    @NotNull(message = "id should be not empty")
    private Long id;
    @Length(min = 4, message = "name can't be less than 4 letters")
    private String name;
    @Length(min = 5, message = "address can't be less than 4 letters")
    private String address;
    private Set<Facility.SportType> sportType;
    private Facility.CoveringType coveringType;
    private Facility.FacilityType facilityType;
    @Length(min = 4, message = "description can't be less than 4 letters")
    private String description;
    private String location;
    @Positive(message = "min booking time should be positive")
    private Integer minBookingTime;
}
