package com.goodamcodes.mapper;

import com.goodamcodes.dto.ExploreDTO;
import com.goodamcodes.model.Explore;
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
public class ExploreMapperImpl implements ExploreMapper {

    @Override
    public Explore toExplore(ExploreDTO exploreDTO) {
        if ( exploreDTO == null ) {
            return null;
        }

        Explore.ExploreBuilder explore = Explore.builder();

        explore.counts( exploreDTO.getCounts() );
        explore.description( exploreDTO.getDescription() );

        return explore.build();
    }

    @Override
    public ExploreDTO toExploreDTO(Explore explore) {
        if ( explore == null ) {
            return null;
        }

        ExploreDTO exploreDTO = new ExploreDTO();

        exploreDTO.setCounts( explore.getCounts() );
        exploreDTO.setDescription( explore.getDescription() );
        exploreDTO.setImage( explore.getImage() );

        return exploreDTO;
    }

    @Override
    public List<ExploreDTO> toExploreDTOs(List<Explore> explores) {
        if ( explores == null ) {
            return null;
        }

        List<ExploreDTO> list = new ArrayList<ExploreDTO>( explores.size() );
        for ( Explore explore : explores ) {
            list.add( toExploreDTO( explore ) );
        }

        return list;
    }

    @Override
    public void updateExploreFromDTO(ExploreDTO exploreDTO, Explore explore) {
        if ( exploreDTO == null ) {
            return;
        }

        explore.setCounts( exploreDTO.getCounts() );
        if ( exploreDTO.getDescription() != null ) {
            explore.setDescription( exploreDTO.getDescription() );
        }
        if ( exploreDTO.getImage() != null ) {
            explore.setImage( exploreDTO.getImage() );
        }
    }
}
