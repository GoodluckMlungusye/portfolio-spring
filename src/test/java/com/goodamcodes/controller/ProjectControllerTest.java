package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.ProjectDTO;
import com.goodamcodes.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Portfolio");

        MockMultipartFile jsonPart = new MockMultipartFile(
                "project", "", "application/json",
                objectMapper.writeValueAsBytes(projectDTO)
        );

        MockMultipartFile filePart = new MockMultipartFile(
                "file", "image.png", "image/png", "dummy image content".getBytes()
        );

        Mockito.when(projectService.addProject(any(), any())).thenReturn(projectDTO);

        mockMvc.perform(multipart("/projects")
                        .file(jsonPart)
                        .file(filePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Portfolio"));
    }

    @Test
    void shouldReturnAllProjects() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Portfolio");

        Mockito.when(projectService.getProjects()).thenReturn(List.of(projectDTO));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Portfolio"));
    }

    @Test
    void shouldUpdateProject() throws Exception {
        ProjectDTO updatedDTO = new ProjectDTO();
        updatedDTO.setName("Updated");

        MockMultipartFile jsonPart = new MockMultipartFile(
                "project", "", "application/json",
                objectMapper.writeValueAsBytes(updatedDTO)
        );

        MockMultipartFile filePart = new MockMultipartFile(
                "file", "image.png", "image/png", "dummy image content".getBytes()
        );

        Mockito.when(projectService.updateProject(eq(1L), any(), any())).thenReturn(updatedDTO);

        mockMvc.perform(multipart("/projects/1")
                        .file(jsonPart)
                        .file(filePart)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void shouldDeleteProject() throws Exception {
        Mockito.when(projectService.deleteProject(1L)).thenReturn("Deleted");

        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}

