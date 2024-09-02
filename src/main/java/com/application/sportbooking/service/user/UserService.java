package com.application.sportbooking.service.user;

import com.application.sportbooking.dto.auth.registration.UserRegistrationRequestDto;
import com.application.sportbooking.dto.auth.registration.UserResponseDto;
import com.application.sportbooking.dto.facility.find.pagination.PaginationArgsRequestDto;
import com.application.sportbooking.dto.profile.update.UpdateProfileRequestDto;
import com.application.sportbooking.exception.RegistrationException;
import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.User;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    User getUserProfile(Long userId);

    User updateProfile(Long userId, MultipartFile avatar,
                       UpdateProfileRequestDto requestDto) throws IOException;

    boolean addFavorite(Long userId, Long facilityId);

    boolean removeFavorite(Long userId, Long facilityId);

    Page<Facility> getUserFavorites(Long userId, PaginationArgsRequestDto paginationArgs);
}
