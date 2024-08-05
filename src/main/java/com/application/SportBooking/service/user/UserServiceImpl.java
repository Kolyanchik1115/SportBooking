package com.application.SportBooking.service.user;

import com.application.bookstore.dto.user.google.GoogleUserDto;
import com.application.bookstore.dto.user.google.UserGoogleRequestDto;
import com.application.bookstore.dto.user.google.UserGoogleResponseDto;
import com.application.bookstore.dto.user.registration.UserRegistrationRequestDto;
import com.application.bookstore.dto.user.registration.UserResponseDto;
import com.application.bookstore.exception.RegistrationException;
import com.application.bookstore.exception.RequestTokenException;
import com.application.bookstore.mapper.UserMapper;
import com.application.bookstore.model.Role;
import com.application.bookstore.model.User;
import com.application.bookstore.repository.role.RoleRepository;
import com.application.bookstore.repository.user.UserRepository;
import com.application.bookstore.security.JwtUtil;
import com.application.bookstore.service.shopping.cart.ShoppingCartService;
import com.application.bookstore.util.RandomPasswordGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String GOOGLE_OAUTH2_URL =
            "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;
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
        user.setRoles(Set.of(roleRepository.findByName(Role.RoleName.USER)));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);
        shoppingCartService.createShoppingCart(savedUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserGoogleResponseDto google(UserGoogleRequestDto requestDto) {
        String googleToken = requestDto.googleToken();

        if (googleToken == null || googleToken.isEmpty()) {
            throw new RequestTokenException("Invalid Google token in request");
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            RandomPasswordGenerator randomGenerator = new RandomPasswordGenerator();

            ResponseEntity<String> response = restTemplate.getForEntity(
                    GOOGLE_OAUTH2_URL + googleToken, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RequestTokenException("Invalid or expired Google token");
            }

            GoogleUserDto googleUserDto = objectMapper.readValue(
                    response.getBody(), GoogleUserDto.class);
            Optional<User> existingUser = userRepository.findByEmail(googleUserDto.getEmail());

            if (existingUser.isPresent()) {
                return new UserGoogleResponseDto(jwtUtil.generateToken(
                        existingUser.get().getEmail()));
            } else {
                User user = userMapper.toUser(googleUserDto);
                user.setRoles(Set.of(roleRepository.findByName(Role.RoleName.USER)));
                user.setPassword(passwordEncoder.encode(randomGenerator.generateRandomPassword()));
                shoppingCartService.createShoppingCart(userRepository.save(user));
                userRepository.save(user);
                return new UserGoogleResponseDto(jwtUtil.generateToken(user.getEmail()));
            }
        } catch (Exception e) {
            throw new RequestTokenException("Invalid or expired Google token");
        }
    }
}
