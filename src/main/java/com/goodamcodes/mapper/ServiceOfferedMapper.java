package com.goodamcodes.mapper;

import com.goodamcodes.dto.ServiceOfferedDTO;
import com.goodamcodes.model.ServiceOffered;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceOfferedMapper {
    @Mapping(target = "image", ignore = true)
    ServiceOffered toServiceOffered(ServiceOfferedDTO serviceOfferedDTO);
    ServiceOfferedDTO toServiceOfferedDTO(ServiceOffered serviceOffered);
    List<ServiceOfferedDTO> toServiceOfferedDTOs(List<ServiceOffered> serviceOffered);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateServiceOfferedFromDTO(ServiceOfferedDTO serviceOfferedDTO, @MappingTarget ServiceOffered serviceOffered);
}
