package com.application.sportbooking.service.user;

import com.application.sportbooking.dto.auth.registration.UserRegistrationRequestDto;
import com.application.sportbooking.dto.auth.registration.UserResponseDto;
import com.application.sportbooking.dto.facility.find.pagination.PaginationArgsRequestDto;
import com.application.sportbooking.dto.profile.update.UpdateProfileRequestDto;
import com.application.sportbooking.exception.EntityNotFoundException;
import com.application.sportbooking.exception.RegistrationException;
import com.application.sportbooking.mapper.UserMapper;
import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.Favorite;
import com.application.sportbooking.model.Role;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.facility.FacilityRepository;
import com.application.sportbooking.repository.favorite.FavoriteRepository;
import com.application.sportbooking.repository.role.RoleRepository;
import com.application.sportbooking.repository.user.UserRepository;
import com.application.sportbooking.security.JwtUtil;
import com.application.sportbooking.service.storage.FileStorageServiceImpl;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final FileStorageServiceImpl fileStorageService;
    private final FacilityRepository facilityRepository;
    private final FavoriteRepository favoriteRepository;

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

    @Override
    public User getUserProfile(Long userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id: + " + userId));
    }

    @Override
    public User updateProfile(Long userId, MultipartFile avatar,
                              UpdateProfileRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id: " + userId));
        if (requestDto.getFullName() != null) {
            user.setFullName(requestDto.getFullName());
        }
        if (requestDto.getDateOfBirth() != null) {
            user.setDateOfBirth(requestDto.getDateOfBirth());
        }
        if (avatar != null) {
            user.setAvatar(fileStorageService.storeFile(avatar));
        }
        return userRepository.save(user);
    }

    @Override
    public boolean addFavorite(Long userId, Long facilityId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user by id: " + userId));
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find facility by id: " + facilityId));
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setFacility(facility);
        favoriteRepository.save(favorite);
        return true;
    }

    @Override
    public boolean removeFavorite(Long userId, Long facilityId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user by id: " + userId));
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find facility by id: " + facilityId));
        Favorite favorite = favoriteRepository.findByUserAndFacility(user, facility)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found"));
        favoriteRepository.delete(favorite);
        return true;
    }

    @Override
    public Page<Facility> getUserFavorites(Long userId, PaginationArgsRequestDto paginationArgs) {
        Pageable pageable = PageRequest.of(paginationArgs.getPage(), paginationArgs.getSize());
        return favoriteRepository.findFavoriteByUser(userId, pageable);
    }
}
