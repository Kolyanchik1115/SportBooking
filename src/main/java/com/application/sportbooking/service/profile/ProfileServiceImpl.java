package com.application.sportbooking.service.profile;

import com.application.sportbooking.dto.profile.UpdateProfileRequestDto;
import com.application.sportbooking.exception.EntityNotFoundException;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public User getUserProfile(Long userId) {
        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id: + " + userId));
    }

    @Override
    public User updateUserProfile(Long userId,
                                  UpdateProfileRequestDto updateProfileRequestDto) {
        User user = profileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id: " + userId));
        if (updateProfileRequestDto.getFullName() != null) {
            user.setFullName(updateProfileRequestDto.getFullName());
        }
        if (updateProfileRequestDto.getDateOfBirth() != null) {
            user.setDateOfBirth(updateProfileRequestDto.getDateOfBirth());
        }
        return profileRepository.save(user);
    }
}

