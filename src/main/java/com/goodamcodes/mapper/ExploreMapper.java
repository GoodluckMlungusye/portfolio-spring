package com.goodamcodes.mapper;

import com.goodamcodes.dto.ExploreDTO;
import com.goodamcodes.model.Explore;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExploreMapper {

    @Mapping(target = "image", ignore = true)
    Explore toExplore(ExploreDTO exploreDTO);
    ExploreDTO toExploreDTO(Explore explore);
    List<ExploreDTO> toExploreDTOs(List<Explore> explores);
    List<Explore> toExplores(List<ExploreDTO> exploreDTOs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExploreFromDTO(ExploreDTO exploreDTO, @MappingTarget Explore explore);

}
