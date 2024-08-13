package com.application.sportbooking.controller;

import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;
import com.application.sportbooking.model.User;
import com.application.sportbooking.service.facility.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

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
        return facilityService.updateFacility(userDetails.getId(), facilityInput.getId(), facilityInput);
    }

    @MutationMapping
    @Operation(summary = "Delete facility", description = "Delete facility")
    public boolean deleteFacility(
            Authentication authentication,
            @Argument("id") Long facilityId) {
        User userDetails = (User) authentication.getPrincipal();
        return facilityService.deleteFacility(userDetails.getId(), facilityId);
    }

//    @QueryMapping
//    @Operation(summary = "Get profile", description = "Get user profile")
//    public User getProfile(Authentication authentication) {
//        User userDetails = (User) authentication.getPrincipal();
//        return profileService.getUserProfile(userDetails.getId());
//    }

//    @MutationMapping
//    @Operation(summary = "Update profile", description = "Update user profile")
//    public User updateProfile(
//            Authentication authentication,
//            @Argument("avatar") MultipartFile avatar,
//            @Argument("profileInput") UpdateProfileRequestDto profileInput) throws IOException {
//        User userDetails = (User) authentication.getPrincipal();
//        return profileService.updateProfile(userDetails.getId(), avatar, profileInput);
//    }
}
