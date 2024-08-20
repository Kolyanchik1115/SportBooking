package com.application.sportbooking.controller;

import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.find.pagination.FacilityFilterRequestDto;
import com.application.sportbooking.dto.facility.find.pagination.PaginationArgsRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.image.UpdateFacilityImageDto;
import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.User;
import com.application.sportbooking.service.facility.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Tag(name = "Profile management", description = "Endpoints for actions with user's profile")
public class FacilityController {
    private final FacilityService facilityService;

    @MutationMapping
    @Operation(summary = "Create facility", description = "Create facility")
    public CreateFacilityResponseDto createFacility(
            Authentication authentication,
            @Argument("facilityInput") @Valid CreateFacilityRequestDto requestDto) {
        User userDetails = (User) authentication.getPrincipal();

        return facilityService.createFacility(userDetails.getId(), requestDto);
    }

    @MutationMapping
    @Operation(summary = "Update facility", description = "Update facility data")
    public UpdateFacilityResponseDto updateFacility(
            Authentication authentication,
            @Argument("facilityInput") @Valid UpdateFacilityRequestDto facilityInput) {
        User userDetails = (User) authentication.getPrincipal();
        return facilityService.updateFacility(userDetails.getId(),
                facilityInput.getId(), facilityInput);
    }

    @MutationMapping
    @Operation(summary = "Delete facility", description = "Delete facility")
    public boolean deleteFacility(
            Authentication authentication,
            @Argument("id") Long facilityId) {
        User userDetails = (User) authentication.getPrincipal();
        return facilityService.deleteFacility(userDetails.getId(), facilityId);
    }

    @QueryMapping
    @Operation(summary = "Find all facilities", description =
            "Find facilities with filters and pagination")
    public Page<Facility> findAll(
            @Argument("paginationArgs") PaginationArgsRequestDto paginationArgs,
            @Argument("facilitiesFilterInput") FacilityFilterRequestDto requestDto) {
        return facilityService.findAll(paginationArgs, requestDto);
    }

    @QueryMapping
    @Operation(summary = "Find one facility", description = "Find one facility")
    public Facility findOne(
            Authentication authentication,
            @Argument("id") Long facilityId) {
        User userDetails = (User) authentication.getPrincipal();
        return facilityService.findOne(userDetails.getId(), facilityId);
    }

    @QueryMapping
    @Operation(summary = "Find by owner", description = "Find by owner")
    public Page<Facility> findOwnerFacilities(
            Authentication authentication,
            @Argument("paginationArgs") PaginationArgsRequestDto paginationArgs) {
        User userDetails = (User) authentication.getPrincipal();
        return facilityService.findOwnerFacilities(userDetails.getId(), paginationArgs);
    }

    @MutationMapping
    @Operation(summary = "Upload facility images", description = "Upload facility images")
    public List<UpdateFacilityImageDto> uploadFacilityImages(
            Authentication authentication,
            @Argument("facilityId") Long facilityId,
            @Argument("images") List<MultipartFile> images) {
        User userDetails = (User) authentication.getPrincipal();
        return facilityService.updateFacilityImages(userDetails.getId(), images, facilityId);
    }
}
