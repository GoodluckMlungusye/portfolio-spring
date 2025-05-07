package com.goodamcodes.service;
import com.goodamcodes.dto.ProjectDTO;
import com.goodamcodes.mapper.ProjectMapper;
import com.goodamcodes.model.Project;
import com.goodamcodes.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    private ProjectDTO projectDTO;
    private Project project;

    @BeforeEach
    void setup() {
        projectDTO = new ProjectDTO();
        projectDTO.setName("MyProject");

        project = new Project();
        project.setName("MyProject");
    }

    @Nested
    @DisplayName("addProject() Tests")
    class AddProjectTests {

        @Test
        void shouldSaveProjectWithImage() {
            MultipartFile file = new MockMultipartFile("image", "image.jpg", "image/jpeg", "fake-image-content".getBytes());

            when(projectRepository.findByName("MyProject")).thenReturn(Optional.empty());
            when(projectMapper.toProject(projectDTO)).thenReturn(project);
            when(imageService.saveImage(file)).thenReturn("image.jpg");
            when(projectRepository.save(project)).thenReturn(project);
            when(projectMapper.toProjectDTO(project)).thenReturn(projectDTO);

            ProjectDTO result = projectService.addProject(projectDTO, file);

            assertNotNull(result);
            verify(imageService).saveImage(file);
            verify(projectRepository).save(project);
            assertEquals("image.jpg", project.getImage());
        }

        @Test
        void shouldThrowWhenProjectNameExists() {
            when(projectRepository.findByName("MyProject")).thenReturn(Optional.of(project));
            assertThrows(IllegalStateException.class, () -> projectService.addProject(projectDTO, null));
            verify(projectRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("addAllProjects() Tests")
    class AddAllProjectsTests {

        @Test
        void shouldSaveOnlyNewProjects() {
            List<ProjectDTO> projectDTOList = List.of(projectDTO);
            List<Project> projectList = List.of(project);
            MultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "test".getBytes());

            when(projectRepository.findByName("MyProject")).thenReturn(Optional.empty());
            when(projectMapper.toProjects(projectDTOList)).thenReturn(projectList);
            when(imageService.handleFilesUpload(List.of(file), 0)).thenReturn("image.jpg");
            when(projectRepository.saveAll(anyList())).thenReturn(projectList);
            when(projectMapper.toProjectDTOs(anyList())).thenReturn(projectDTOList);

            List<ProjectDTO> savedProjects = projectService.addAllProjects(projectDTOList, List.of(file));

            assertEquals(1, savedProjects.size());
            verify(projectRepository).saveAll(anyList());
        }
    }

    @Nested
    @DisplayName("updateProject() Tests")
    class UpdateProjectTests {

        @Test
        void shouldUpdateExistingProject() {
            MultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "test".getBytes());
            project.setImage("old.jpg");

            when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
            doNothing().when(imageService).deleteImage("old.jpg");
            when(imageService.saveImage(file)).thenReturn("image.jpg");
            doNothing().when(projectMapper).updateProjectFromDTO(projectDTO, project);
            when(projectRepository.save(project)).thenReturn(project);
            when(projectMapper.toProjectDTO(project)).thenReturn(projectDTO);

            ProjectDTO result = projectService.updateProject(1L, projectDTO, file);

            assertNotNull(result);
            assertEquals("image.jpg", project.getImage());
            verify(imageService).deleteImage("old.jpg");
            verify(imageService).saveImage(file);
        }

        @Test
        void shouldThrowIfProjectNotFound() {
            when(projectRepository.findById(999L)).thenReturn(Optional.empty());
            assertThrows(IllegalStateException.class, () -> projectService.updateProject(999L, projectDTO, null));
        }
    }

    @Nested
    @DisplayName("deleteProject() Tests")
    class DeleteProjectTests {

        @Test
        void shouldRemoveProjectAndImage() {
            project.setImage("delete.jpg");

            when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
            doNothing().when(imageService).deleteImage("delete.jpg");

            String response = projectService.deleteProject(1L);

            assertTrue(response.contains("1"));
            verify(imageService).deleteImage("delete.jpg");
            verify(projectRepository).deleteById(1L);
        }

        @Test
        void shouldThrowIfNotFound() {
            when(projectRepository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(IllegalStateException.class, () -> projectService.deleteProject(99L));
        }
    }

    @Nested
    @DisplayName("getProjects() Tests")
    class GetProjectsTests {

        @Test
        void shouldReturnMappedDTOList() {
            List<Project> projectList = List.of(project);
            List<ProjectDTO> dtoList = List.of(projectDTO);

            when(projectRepository.findAll()).thenReturn(projectList);
            when(projectMapper.toProjectDTOs(projectList)).thenReturn(dtoList);

            List<ProjectDTO> result = projectService.getProjects();

            assertEquals(1, result.size());
            verify(projectRepository).findAll();
            verify(projectMapper).toProjectDTOs(projectList);
        }
    }
}
