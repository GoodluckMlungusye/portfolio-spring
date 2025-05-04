package com.goodamcodes.service;

import com.goodamcodes.dto.ProjectDTO;
import com.goodamcodes.mapper.ProjectMapper;
import com.goodamcodes.model.Project;
import com.goodamcodes.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProjectMapper projectMapper;

    public ProjectDTO addProject(ProjectDTO projectDTO, MultipartFile file){
        Optional<Project> existingProject = projectRepository.findByName(projectDTO.getName());
        if(existingProject.isPresent()){
            throw new IllegalStateException("Project name exists");
        }

        Project project = projectMapper.toProject(projectDTO);
        if(file != null && !file.isEmpty()){
            String fileName = imageService.saveImage(file);
            project.setImage(fileName);
        }
        Project savedProject =  projectRepository.save(project);
        return projectMapper.toProjectDTO(savedProject);
    }

    public List<ProjectDTO> addAllProjects(List<ProjectDTO> projectDTOs, List<MultipartFile> files) {
        List<Project> projects = projectMapper.toProjects(projectDTOs);

        List<Project> newProjects = IntStream.range(0, projects.size())
                .filter(i -> projectRepository.findByName(projectDTOs.get(i).getName()).isEmpty())
                .mapToObj(i -> {
                    Project project = projects.get(i);
                    String fileName = imageService.handleFilesUpload(files, i);
                    if (fileName != null) {
                        project.setImage(fileName);
                    }
                    return project;
                })
                .collect(Collectors.toList());

        List<Project> savedProjects = projectRepository.saveAll(newProjects);
        return projectMapper.toProjectDTOs(savedProjects);
    }


    public List<ProjectDTO> getProjects(){
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toProjectDTOs(projects);
    }

    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO, MultipartFile file) {
        Project existingProject = projectRepository.findById(projectId).orElseThrow(
                () -> new IllegalStateException("Project does not exist")
        );

        projectMapper.updateProjectFromDTO(projectDTO, existingProject);

        if(file != null && !file.isEmpty()){
            imageService.deleteImage(existingProject.getImage());
            String fileName = imageService.saveImage(file);
            existingProject.setImage(fileName);
        }

        Project updatedProject = projectRepository.save(existingProject);
        return projectMapper.toProjectDTO(updatedProject);
    }


    public String deleteProject(Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new IllegalStateException("Project does not exist")
        );
        imageService.deleteImage(project.getImage());
        projectRepository.deleteById(projectId);
        return "Project with id: " + projectId + " has been deleted";
    }

}
