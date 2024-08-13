package com.application.sportbooking.mapper;

import com.application.sportbooking.config.MapperConfig;
import com.application.sportbooking.dto.facility.create.CreateFacilityRequestDto;
import com.application.sportbooking.dto.facility.create.CreateFacilityResponseDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityRequestDto;
import com.application.sportbooking.dto.facility.update.UpdateFacilityResponseDto;
import com.application.sportbooking.model.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE)
public interface FacilityMapper {

    @Mapping(source = "description", target = "description")
    Facility toFacility(CreateFacilityRequestDto dto);

    CreateFacilityResponseDto toDto(Facility facility);

    UpdateFacilityResponseDto toUpdateFacilityDto(Facility facility);

    void updateFacilityFromDto(UpdateFacilityRequestDto dto,
                               @MappingTarget Facility facility);

}
