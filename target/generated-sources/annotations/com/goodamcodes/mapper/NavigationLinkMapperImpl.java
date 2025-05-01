package com.goodamcodes.mapper;

import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.model.NavigationLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-01T18:50:32+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class NavigationLinkMapperImpl implements NavigationLinkMapper {

    @Override
    public NavigationLink toNavigationLink(NavigationLinkDTO navigationLinkDTO) {
        if ( navigationLinkDTO == null ) {
            return null;
        }

        NavigationLink.NavigationLinkBuilder navigationLink = NavigationLink.builder();

        navigationLink.name( navigationLinkDTO.getName() );

        return navigationLink.build();
    }

    @Override
    public NavigationLinkDTO toNavigationLinkDTO(NavigationLink navigationLink) {
        if ( navigationLink == null ) {
            return null;
        }

        NavigationLinkDTO navigationLinkDTO = new NavigationLinkDTO();

        navigationLinkDTO.setName( navigationLink.getName() );

        return navigationLinkDTO;
    }

    @Override
    public List<NavigationLinkDTO> toNavigationLinkDTOs(List<NavigationLink> navigationLinks) {
        if ( navigationLinks == null ) {
            return null;
        }

        List<NavigationLinkDTO> list = new ArrayList<NavigationLinkDTO>( navigationLinks.size() );
        for ( NavigationLink navigationLink : navigationLinks ) {
            list.add( toNavigationLinkDTO( navigationLink ) );
        }

        return list;
    }

    @Override
    public void updateNavigationLinkFromDTO(NavigationLinkDTO navigationLinkDTO, NavigationLink navigationLink) {
        if ( navigationLinkDTO == null ) {
            return;
        }

        if ( navigationLinkDTO.getName() != null ) {
            navigationLink.setName( navigationLinkDTO.getName() );
        }
    }
}
