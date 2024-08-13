package com.application.sportbooking.service.facility;

import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;
import com.application.sportbooking.exception.EntityNotFoundException;
import com.application.sportbooking.mapper.FacilityMapper;
import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.facility.FacilityRepository;
import com.application.sportbooking.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final FacilityMapper facilityMapper;

    @Override
    public CreateFacilityResponseDto createFacility(Long userId,
                                                    CreateFacilityRequestDto requestDto) {
        Facility facility = facilityMapper.toFacility(requestDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        facility.setUser(user);
        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    @Override
    public UpdateFacilityResponseDto updateFacility(
            Long userId, Long facilityId, UpdateFacilityRequestDto requestDto) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility not found"));
        facilityMapper.updateFacilityFromDto(requestDto, facility);
        return facilityMapper.toUpdateFacilityDto(facilityRepository.save(facility));
    }

    @Override
    public boolean deleteFacility(Long userId, Long facilityId) {
        if (!facilityRepository.existsById(facilityId)) {
            throw new EntityNotFoundException("Can't find facility by id: " + facilityId);
        }
        facilityRepository.deleteById(facilityId);
        return true;
    }
}

