package com.application.sportbooking.service.facility;

import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.find.pagination.FacilityFilterRequestDto;
import com.application.sportbooking.dto.facility.find.pagination.PaginationArgsRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.image.UpdateFacilityImageDto;
import com.application.sportbooking.model.Facility;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

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

    Page<Facility> findAll(PaginationArgsRequestDto pageable, FacilityFilterRequestDto requestDto);

    Facility findOne(Long userId,
                     Long facilityId);

    Page<Facility> findOwnerFacilities(Long userId, PaginationArgsRequestDto pageable);

    List<UpdateFacilityImageDto> updateFacilityImages(Long userId,
                                                      List<MultipartFile> images,
                                                      Long facilityId);
}
