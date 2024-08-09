package com.application.sportbooking.service.profile;

import com.application.sportbooking.dto.profile.UpdateProfileRequestDto;
import com.application.sportbooking.exception.EntityNotFoundException;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.profile.ProfileRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public User updateProfile(Long userId, MultipartFile avatar,
                              UpdateProfileRequestDto updateProfileRequestDto) throws IOException {
        User user = profileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id: " + userId));
        if (updateProfileRequestDto.getFullName() != null) {
            user.setFullName(updateProfileRequestDto.getFullName());
        }
        if (updateProfileRequestDto.getDateOfBirth() != null) {
            user.setDateOfBirth(updateProfileRequestDto.getDateOfBirth());
        }
        if (avatar != null) {
            String fileName = UUID.randomUUID() + "_" + avatar.getOriginalFilename();
            Files.write(Paths.get(
                    "src/main/resources/files/avatar/", fileName), avatar.getBytes());
            user.setAvatar(fileName);
        }
        return profileRepository.save(user);
    }
}

