package com.goodamcodes.mapper;

import com.goodamcodes.dto.ProjectDTO;
import com.goodamcodes.model.Project;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "image", ignore = true)
    Project toProject(ProjectDTO projectDTO);
    ProjectDTO toProjectDTO(Project project);
    List<ProjectDTO> toProjectDTOs(List<Project> projects);
    List<Project> toProjects(List<ProjectDTO> projectDTOS);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProjectFromDTO(ProjectDTO projectDTO, @MappingTarget Project project);
}
