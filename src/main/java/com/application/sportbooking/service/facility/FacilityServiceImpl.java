package com.application.sportbooking.service.facility;

import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.find.pagination.FacilityFilterRequestDto;
import com.application.sportbooking.dto.facility.find.pagination.PaginationArgsRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.image.UpdateFacilityImageDto;
import com.application.sportbooking.exception.EntityNotFoundException;
import com.application.sportbooking.mapper.FacilityMapper;
import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.FacilityImage;
import com.application.sportbooking.model.User;
import com.application.sportbooking.repository.facility.FacilityImageRepository;
import com.application.sportbooking.repository.facility.FacilityRepository;
import com.application.sportbooking.repository.user.UserRepository;
import com.application.sportbooking.service.storage.FileStorageService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final FacilityMapper facilityMapper;
    private final FileStorageService fileStorageService;
    private final FacilityImageRepository facilityImageRepository;

    @Override
    public CreateFacilityResponseDto createFacility(Long userId,
                                                    CreateFacilityRequestDto requestDto) {
        Facility facility = facilityMapper.toFacility(requestDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        facility.setUser(user);
        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    @Override
    public UpdateFacilityResponseDto updateFacility(
            Long userId, Long facilityId, UpdateFacilityRequestDto requestDto) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility not found"));
        facilityMapper.updateFacilityFromDto(requestDto, facility);
        return facilityMapper.toUpdateFacilityDto(facilityRepository.save(facility));
    }

    @Override
    public boolean deleteFacility(Long userId, Long facilityId) {
        if (!facilityRepository.existsById(facilityId)) {
            throw new EntityNotFoundException("Can't find facility by id: " + facilityId);
        }
        facilityRepository.deleteById(facilityId);
        return true;
    }

    @Override
    public Page<Facility> findAll(PaginationArgsRequestDto paginationArgs,
                                  FacilityFilterRequestDto filter) {
        Pageable pageable = PageRequest.of(paginationArgs.getPage(), paginationArgs.getSize());

        if (filter == null || (filter.getSportType() == null
                && filter.getFacilityType() == null && filter.getCoveringType() == null)) {
            return facilityRepository.findAll(pageable);
        }

        return facilityRepository.findAllWithFilter(
                filter.getSportType() != null ? filter.getSportType().stream().map(Enum::name)
                        .toArray(String[]::new) : new String[0],
                filter.getFacilityType() != null ? filter.getFacilityType().name() : null,
                filter.getCoveringType() != null ? filter.getCoveringType().name() : null,
                pageable);
    }

    @Override
    public Facility findOne(Long userId, Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility not found"));
    }

    @Override
    public Page<Facility> findOwnerFacilities(Long userId,
                                              PaginationArgsRequestDto paginationArgs) {
        Pageable pageable = PageRequest.of(paginationArgs.getPage(), paginationArgs.getSize());
        return facilityRepository.findOwnerFacilities(pageable, userId);
    }

    @Override
    public List<UpdateFacilityImageDto> updateFacilityImages(
            Long userId, List<MultipartFile> images, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Facility not found with id: " + facilityId));

        List<FacilityImage> facilityImages = images.stream()
                .map(image -> {
                    String imageName = fileStorageService.storeFile(image);
                    FacilityImage facilityImage = new FacilityImage();
                    facilityImage.setFacility(facility);
                    facilityImage.setImage(imageName);
                    return facilityImage;
                })
                .collect(Collectors.toList());

        return facilityImageRepository.saveAll(facilityImages).stream()
                .map(facilityMapper::toUpdateImageDto)
                .collect(Collectors.toList());
    }
}

