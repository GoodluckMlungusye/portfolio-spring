package com.goodamcodes.mapper;

import com.goodamcodes.dto.ServiceOfferedDTO;
import com.goodamcodes.model.ServiceOffered;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T16:01:13+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ServiceOfferedMapperImpl implements ServiceOfferedMapper {

    @Override
    public ServiceOffered toServiceOffered(ServiceOfferedDTO serviceOfferedDTO) {
        if ( serviceOfferedDTO == null ) {
            return null;
        }

        ServiceOffered.ServiceOfferedBuilder serviceOffered = ServiceOffered.builder();

        serviceOffered.name( serviceOfferedDTO.getName() );
        serviceOffered.description( serviceOfferedDTO.getDescription() );

        return serviceOffered.build();
    }

    @Override
    public ServiceOfferedDTO toServiceOfferedDTO(ServiceOffered serviceOffered) {
        if ( serviceOffered == null ) {
            return null;
        }

        ServiceOfferedDTO serviceOfferedDTO = new ServiceOfferedDTO();

        serviceOfferedDTO.setName( serviceOffered.getName() );
        serviceOfferedDTO.setDescription( serviceOffered.getDescription() );
        serviceOfferedDTO.setImage( serviceOffered.getImage() );

        return serviceOfferedDTO;
    }

    @Override
    public List<ServiceOfferedDTO> toServiceOfferedDTOs(List<ServiceOffered> serviceOffered) {
        if ( serviceOffered == null ) {
            return null;
        }

        List<ServiceOfferedDTO> list = new ArrayList<ServiceOfferedDTO>( serviceOffered.size() );
        for ( ServiceOffered serviceOffered1 : serviceOffered ) {
            list.add( toServiceOfferedDTO( serviceOffered1 ) );
        }

        return list;
    }

    @Override
    public void updateServiceOfferedFromDTO(ServiceOfferedDTO serviceOfferedDTO, ServiceOffered serviceOffered) {
        if ( serviceOfferedDTO == null ) {
            return;
        }

        if ( serviceOfferedDTO.getName() != null ) {
            serviceOffered.setName( serviceOfferedDTO.getName() );
        }
        if ( serviceOfferedDTO.getDescription() != null ) {
            serviceOffered.setDescription( serviceOfferedDTO.getDescription() );
        }
        if ( serviceOfferedDTO.getImage() != null ) {
            serviceOffered.setImage( serviceOfferedDTO.getImage() );
        }
    }
}
