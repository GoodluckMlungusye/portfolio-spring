package com.goodamcodes.mapper;

import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.model.NavigationLink;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NavigationLinkMapper {

    NavigationLink toNavigationLink(NavigationLinkDTO navigationLinkDTO);
    NavigationLinkDTO toNavigationLinkDTO(NavigationLink navigationLink);
    List<NavigationLinkDTO> toNavigationLinkDTOs(List<NavigationLink> navigationLinks);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNavigationLinkFromDTO(NavigationLinkDTO navigationLinkDTO, @MappingTarget NavigationLink navigationLink);

}
