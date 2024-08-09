package com.application.sportbooking.service.profile;

import com.application.sportbooking.dto.profile.UpdateProfileRequestDto;
import com.application.sportbooking.model.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    User getUserProfile(Long userId);

    User updateProfile(Long userId, MultipartFile avatar,
                       UpdateProfileRequestDto requestDto) throws IOException;
}
