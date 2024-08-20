package com.application.sportbooking.dto.facility.find.pagination;

import com.application.sportbooking.model.Facility.CoveringType;
import com.application.sportbooking.model.Facility.FacilityType;
import com.application.sportbooking.model.Facility.SportType;
import java.util.Set;
import lombok.Data;

@Data
public class FacilityFilterRequestDto {
    private Set<SportType> sportType;
    private FacilityType facilityType;
    private CoveringType coveringType;
}
