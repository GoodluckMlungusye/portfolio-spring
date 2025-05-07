package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.service.NavigationLinkService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NavigationLinkController.class)
class NavigationLinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NavigationLinkService navigationLinkService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddNavigationLink() throws Exception {
        NavigationLinkDTO linkDTO = new NavigationLinkDTO();
        linkDTO.setId(1L);
        linkDTO.setName("Home");

        when(navigationLinkService.addNavigationLink(any())).thenReturn(linkDTO);

        mockMvc.perform(post("/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(linkDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Home"));
    }

    @Test
    void shouldAddAllNavigationLinks() throws Exception {
        NavigationLinkDTO link1 = new NavigationLinkDTO();
        link1.setId(1L);
        link1.setName("Home");

        NavigationLinkDTO link2 = new NavigationLinkDTO();
        link2.setId(2L);
        link2.setName("About");

        List<NavigationLinkDTO> links = List.of(link1, link2);

        when(navigationLinkService.addAllNavigationLinks(any())).thenReturn(links);

        mockMvc.perform(post("/links/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(links)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Home"))
                .andExpect(jsonPath("$[1].name").value("About"));
    }

    @Test
    void shouldGetNavigationLinks() throws Exception {
        NavigationLinkDTO link = new NavigationLinkDTO();
        link.setId(1L);
        link.setName("Home");

        when(navigationLinkService.getNavigationLinks()).thenReturn(List.of(link));

        mockMvc.perform(get("/links"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Home"));
    }

    @Test
    void shouldUpdateNavigationLink() throws Exception {
        NavigationLinkDTO updatedLink = new NavigationLinkDTO();
        updatedLink.setId(1L);
        updatedLink.setName("Updated Link");

        when(navigationLinkService.updateNavigationLink(Mockito.eq(1L), any())).thenReturn(updatedLink);

        mockMvc.perform(patch("/links/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLink)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Link"));
    }

    @Test
    void shouldDeleteNavigationLink() throws Exception {
        when(navigationLinkService.deleteNavigationLink(1L)).thenReturn("Navigation link with id: 1 has been deleted");

        mockMvc.perform(delete("/links/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Navigation link with id: 1 has been deleted"));
    }
}

