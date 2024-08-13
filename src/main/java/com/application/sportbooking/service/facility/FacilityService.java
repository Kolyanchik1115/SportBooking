package com.application.sportbooking.service.facility;

import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;

public interface FacilityService {
    CreateFacilityResponseDto createFacility(
            Long userId,
            CreateFacilityRequestDto requestDto);

    UpdateFacilityResponseDto updateFacility(
            Long userId,
            Long facilityId,
            UpdateFacilityRequestDto requestDto);

    boolean deleteFacility(
            Long userId,
            Long facilityId);
}
