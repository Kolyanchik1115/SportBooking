package com.application.SportBooking.mapper;

import com.application.bookstore.config.MapperConfig;
import com.application.bookstore.dto.user.google.GoogleUserDto;
import com.application.bookstore.dto.user.registration.UserRegistrationRequestDto;
import com.application.bookstore.dto.user.registration.UserResponseDto;
import com.application.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "firstName", source = "givenName")
    @Mapping(target = "lastName", source = "familyName")
    User toUser(GoogleUserDto googleUserDto);

    User toUser(UserRegistrationRequestDto requestDto);
}
