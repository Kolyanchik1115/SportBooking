package com.application.sportbooking.controller;

import com.application.sportbooking.dto.facility.find.pagination.PaginationArgsRequestDto;
import com.application.sportbooking.dto.profile.update.UpdateProfileRequestDto;
import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.User;
import com.application.sportbooking.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
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
@Tag(name = "User management", description = "Endpoints for actions with user")
public class UserController {
    private final UserService userService;

    @QueryMapping
    @Operation(summary = "Get profile", description = "Get user profile")
    public User getProfile(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        return userService.getUserProfile(userDetails.getId());
    }

    @MutationMapping
    @Operation(summary = "Update profile", description = "Update user profile")
    public User updateProfile(
            Authentication authentication,
            @Argument("avatar") MultipartFile avatar,
            @Argument("profileInput") UpdateProfileRequestDto profileInput) throws IOException {
        User userDetails = (User) authentication.getPrincipal();
        return userService.updateProfile(userDetails.getId(), avatar, profileInput);
    }

    @MutationMapping
    @Operation(summary = "Add facility to favorite", description = "Add facility to favorite")
    public boolean addFavorite(
            Authentication authentication,
            @Argument("facilityId") Long facilityId) {
        User userDetails = (User) authentication.getPrincipal();
        return userService.addFavorite(userDetails.getId(), facilityId);
    }

    @MutationMapping
    @Operation(summary = "Remove facility to favorite", description = "Remove facility to favorite")
    public boolean removeFavorite(
            Authentication authentication,
            @Argument("facilityId") Long facilityId) {
        User userDetails = (User) authentication.getPrincipal();
        return userService.removeFavorite(userDetails.getId(), facilityId);
    }

    @QueryMapping
    @Operation(summary = "Get user favorites", description = "Get user favorites")
    public Page<Facility> getUserFavorites(
            Authentication authentication,
            @Argument("paginationArgs") PaginationArgsRequestDto paginationArgs) {
        User userDetails = (User) authentication.getPrincipal();
        return userService.getUserFavorites(userDetails.getId(), paginationArgs);
    }
}
