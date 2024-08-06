package com.application.sportbooking.service.profile;

import com.application.sportbooking.dto.profile.UpdateProfileRequestDto;
import com.application.sportbooking.model.User;

public interface ProfileService {
    User getUserProfile(Long userId);

    User updateUserProfile(Long userId, UpdateProfileRequestDto requestDto);
}
