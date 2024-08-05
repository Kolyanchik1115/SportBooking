package com.application.sportbooking.mapper;

import com.application.sportbooking.config.MapperConfig;
import com.application.sportbooking.dto.user.registration.UserRegistrationRequestDto;
import com.application.sportbooking.dto.user.registration.UserResponseDto;
import com.application.sportbooking.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "accessToken", source = "token")
    UserResponseDto toDto(User user, String token);

    User toUser(UserRegistrationRequestDto requestDto);
}
