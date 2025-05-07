package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.ExploreDTO;
import com.goodamcodes.service.ExploreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExploreController.class)
class ExploreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExploreService exploreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddExplore() throws Exception {
        ExploreDTO exploreDTO = new ExploreDTO();
        exploreDTO.setCounts(1);
        exploreDTO.setDescription("A thrilling journey");
        exploreDTO.setImage("image.jpg");

        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "fake image content".getBytes());
        MockMultipartFile projectPart = new MockMultipartFile("explore", "", "application/json", objectMapper.writeValueAsBytes(exploreDTO));

        when(exploreService.addExplore(any(ExploreDTO.class), any())).thenReturn(exploreDTO);

        mockMvc.perform(multipart("/explore")
                        .file(projectPart)
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.counts").value(1))
                .andExpect(jsonPath("$.description").value("A thrilling journey"))
                .andExpect(jsonPath("$.image").value("image.jpg"));
    }

    @Test
    void shouldAddAllExplores() throws Exception {
        ExploreDTO dto1 = new ExploreDTO();
        dto1.setCounts(10);
        dto1.setDescription("Desc 1");
        dto1.setImage("img1.jpg");

        ExploreDTO dto2 = new ExploreDTO();
        dto2.setCounts(20);
        dto2.setDescription("Desc 2");
        dto2.setImage("img2.jpg");

        List<ExploreDTO> exploreDTOs = List.of(dto1, dto2);

        MockMultipartFile files1 = new MockMultipartFile("files", "img1.jpg", MediaType.IMAGE_JPEG_VALUE, "img1".getBytes());
        MockMultipartFile files2 = new MockMultipartFile("files", "img2.jpg", MediaType.IMAGE_JPEG_VALUE, "img2".getBytes());
        MockMultipartFile dtoPart = new MockMultipartFile("explores", "", "application/json", objectMapper.writeValueAsBytes(exploreDTOs));

        when(exploreService.addAllExplores(any(), any())).thenReturn(exploreDTOs);

        mockMvc.perform(multipart("/explore/all")
                        .file(dtoPart)
                        .file(files1)
                        .file(files2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].counts").value(10))
                .andExpect(jsonPath("$[1].description").value("Desc 2"));
    }

    @Test
    void shouldGetExploreList() throws Exception {
        ExploreDTO exploreDTO = new ExploreDTO();
        exploreDTO.setCounts(5);
        exploreDTO.setDescription("Nature trip");
        exploreDTO.setImage("nature.jpg");

        when(exploreService.getExploreList()).thenReturn(List.of(exploreDTO));

        mockMvc.perform(get("/explore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].counts").value(5))
                .andExpect(jsonPath("$[0].description").value("Nature trip"))
                .andExpect(jsonPath("$[0].image").value("nature.jpg"));
    }

    @Test
    void shouldUpdateExplore() throws Exception {
        ExploreDTO exploreDTO = new ExploreDTO();
        exploreDTO.setCounts(99);
        exploreDTO.setDescription("Updated adventure");
        exploreDTO.setImage("newimage.jpg");

        MockMultipartFile file = new MockMultipartFile("file", "newimage.jpg", MediaType.IMAGE_JPEG_VALUE, "updated".getBytes());
        MockMultipartFile explorePart = new MockMultipartFile("explore", "", "application/json", objectMapper.writeValueAsBytes(exploreDTO));

        when(exploreService.updateExplore(Mockito.eq(1L), any(), any())).thenReturn(exploreDTO);

        mockMvc.perform(multipart("/explore/1")
                        .file(file)
                        .file(explorePart)
                        .with(request -> {
                            request.setMethod("PATCH"); // override to PATCH
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.counts").value(99))
                .andExpect(jsonPath("$.description").value("Updated adventure"))
                .andExpect(jsonPath("$.image").value("newimage.jpg"));
    }

    @Test
    void shouldDeleteExplore() throws Exception {
        when(exploreService.deleteExplore(1L)).thenReturn("Explore with id: 1 has been deleted");

        mockMvc.perform(delete("/explore/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Explore with id: 1 has been deleted"));
    }
}
