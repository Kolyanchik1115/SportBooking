package com.application.sportbooking.service.user;

import com.application.sportbooking.dto.user.registration.UserRegistrationRequestDto;
import com.application.sportbooking.dto.user.registration.UserResponseDto;
import com.application.sportbooking.exception.RegistrationException;
import com.application.sportbooking.mapper.UserMapper;
import com.application.sportbooking.model.Role;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.role.RoleRepository;
import com.application.sportbooking.repository.user.UserRepository;
import com.application.sportbooking.security.JwtUtil;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(String.format("Can't register user with email %s",
                    requestDto.getEmail()));
        }
        User user = userMapper.toUser(requestDto);
        String token = jwtUtil.generateToken(requestDto.getEmail());
        user.setRoles(Set.of(roleRepository.findByName(Role.RoleName.USER)));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return userMapper.toDto(userRepository.save(user), token);
    }
}
