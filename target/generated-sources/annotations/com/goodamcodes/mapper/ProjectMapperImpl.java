package com.goodamcodes.mapper;

import com.goodamcodes.dto.ProjectDTO;
import com.goodamcodes.model.Project;
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
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toProject(ProjectDTO projectDTO) {
        if ( projectDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.name( projectDTO.getName() );
        project.technology( projectDTO.getTechnology() );
        project.rate( projectDTO.getRate() );
        project.repository( projectDTO.getRepository() );
        project.colorCode( projectDTO.getColorCode() );
        project.isHosted( projectDTO.getIsHosted() );

        return project.build();
    }

    @Override
    public ProjectDTO toProjectDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setName( project.getName() );
        projectDTO.setTechnology( project.getTechnology() );
        projectDTO.setRate( project.getRate() );
        projectDTO.setRepository( project.getRepository() );
        projectDTO.setColorCode( project.getColorCode() );
        projectDTO.setImage( project.getImage() );
        projectDTO.setIsHosted( project.getIsHosted() );

        return projectDTO;
    }

    @Override
    public List<ProjectDTO> toProjectDTOs(List<Project> projects) {
        if ( projects == null ) {
            return null;
        }

        List<ProjectDTO> list = new ArrayList<ProjectDTO>( projects.size() );
        for ( Project project : projects ) {
            list.add( toProjectDTO( project ) );
        }

        return list;
    }

    @Override
    public void updateProjectFromDTO(ProjectDTO projectDTO, Project project) {
        if ( projectDTO == null ) {
            return;
        }

        if ( projectDTO.getName() != null ) {
            project.setName( projectDTO.getName() );
        }
        if ( projectDTO.getTechnology() != null ) {
            project.setTechnology( projectDTO.getTechnology() );
        }
        project.setRate( projectDTO.getRate() );
        if ( projectDTO.getRepository() != null ) {
            project.setRepository( projectDTO.getRepository() );
        }
        if ( projectDTO.getColorCode() != null ) {
            project.setColorCode( projectDTO.getColorCode() );
        }
        if ( projectDTO.getImage() != null ) {
            project.setImage( projectDTO.getImage() );
        }
        if ( projectDTO.getIsHosted() != null ) {
            project.setIsHosted( projectDTO.getIsHosted() );
        }
    }
}
