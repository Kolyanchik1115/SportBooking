package com.application.sportbooking.controller;

import com.application.sportbooking.dto.profile.UpdateProfileRequestDto;
import com.application.sportbooking.model.User;
import com.application.sportbooking.service.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Tag(name = "Profile management", description = "Endpoints for actions with user's profile")
public class UserProfileController {
    private final ProfileService profileService;

    @QueryMapping
    @Operation(summary = "Get profile", description = "Get user profile")
    public User getProfile(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        return profileService.getUserProfile(userDetails.getId());
    }

    @MutationMapping
    @Operation(summary = "Update profile", description = "Update user profile")
    public User updateProfile(
            Authentication authentication,
            @Argument("profileInput") @Valid UpdateProfileRequestDto requestDto) {
        User userDetails = (User) authentication.getPrincipal();
        return profileService.updateUserProfile(userDetails.getId(), requestDto);
    }

}
