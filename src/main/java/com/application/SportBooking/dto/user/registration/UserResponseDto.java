package com.application.SportBooking.dto.user.registration;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String deliveryAddress;
}
