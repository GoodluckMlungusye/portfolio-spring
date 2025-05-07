package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.ServiceOfferedDTO;
import com.goodamcodes.service.ServiceOfferedService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceOfferedController.class)
class ServiceOfferedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceOfferedService serviceOfferedService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddServiceOffered() throws Exception {
        ServiceOfferedDTO dto = new ServiceOfferedDTO();
        dto.setId(1L);
        dto.setName("Web Development");
        dto.setDescription("Building websites");
        dto.setImage("image.jpg");

        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "dummy".getBytes());
        MockMultipartFile jsonPart = new MockMultipartFile("service", "", "application/json", objectMapper.writeValueAsBytes(dto));

        Mockito.when(serviceOfferedService.addServiceOffered(any(), any())).thenReturn(dto);

        mockMvc.perform(multipart("/services")
                        .file(jsonPart)
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Web Development"))
                .andExpect(jsonPath("$.description").value("Building websites"));
    }

    @Test
    void shouldAddAllServicesOffered() throws Exception {
        ServiceOfferedDTO dto1 = new ServiceOfferedDTO();
        dto1.setId(1L);
        dto1.setName("Service 1");

        ServiceOfferedDTO dto2 = new ServiceOfferedDTO();
        dto2.setId(2L);
        dto2.setName("Service 2");

        List<ServiceOfferedDTO> services = List.of(dto1, dto2);

        MockMultipartFile file1 = new MockMultipartFile("files", "img1.jpg", "image/jpeg", "img1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "img2.jpg", "image/jpeg", "img2".getBytes());

        MockMultipartFile serviceJson = new MockMultipartFile("services", "", "application/json", objectMapper.writeValueAsBytes(services));

        Mockito.when(serviceOfferedService.addAllServicesOffered(any(), any())).thenReturn(services);

        mockMvc.perform(multipart("/services/all")
                        .file(serviceJson)
                        .file(file1)
                        .file(file2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldGetServiceOfferedList() throws Exception {
        ServiceOfferedDTO dto = new ServiceOfferedDTO();
        dto.setId(1L);
        dto.setName("UI/UX");

        Mockito.when(serviceOfferedService.getServiceOfferedList()).thenReturn(List.of(dto));

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("UI/UX"));
    }

    @Test
    void shouldUpdateServiceOffered() throws Exception {
        ServiceOfferedDTO updated = new ServiceOfferedDTO();
        updated.setId(1L);
        updated.setName("Updated Service");

        MockMultipartFile file = new MockMultipartFile("file", "new-img.jpg", "image/jpeg", "img".getBytes());
        MockMultipartFile json = new MockMultipartFile("service", "", "application/json", objectMapper.writeValueAsBytes(updated));

        Mockito.when(serviceOfferedService.updateServiceOffered(eq(1L), any(), any())).thenReturn(updated);

        mockMvc.perform(multipart("/services/1")
                        .file(json)
                        .file(file)
                        .with(req -> {
                            req.setMethod("PATCH");
                            return req;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Service"));
    }

    @Test
    void shouldDeleteServiceOffered() throws Exception {
        Mockito.when(serviceOfferedService.deleteServiceOffered(1L)).thenReturn("Deleted");

        mockMvc.perform(delete("/services/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}
