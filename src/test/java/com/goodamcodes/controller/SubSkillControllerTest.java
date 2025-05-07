package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.service.SubSkillService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubSkillController.class)
class SubSkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubSkillService subSkillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddSubSkill() throws Exception {
        SubSkillDTO subSkill = new SubSkillDTO();
        subSkill.setId(1L);
        subSkill.setName("Java");
        subSkill.setPercentageLevel(90);
        subSkill.setSkillId(100L);

        Mockito.when(subSkillService.addSubSkill(any())).thenReturn(subSkill);

        mockMvc.perform(post("/subskills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subSkill)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.percentageLevel").value(90))
                .andExpect(jsonPath("$.skillId").value(100));
    }

    @Test
    void shouldAddAllSubSkills() throws Exception {
        SubSkillDTO one = new SubSkillDTO();
        one.setId(1L);
        one.setName("Java");
        one.setPercentageLevel(90);
        one.setSkillId(100L);

        SubSkillDTO two = new SubSkillDTO();
        two.setId(2L);
        two.setName("Python");
        two.setPercentageLevel(85);
        two.setSkillId(100L);

        List<SubSkillDTO> list = List.of(one, two);

        Mockito.when(subSkillService.addAllSubSkills(any())).thenReturn(list);

        mockMvc.perform(post("/subskills/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldGetSubSkills() throws Exception {
        SubSkillDTO subSkill = new SubSkillDTO();
        subSkill.setId(1L);
        subSkill.setName("Go");
        subSkill.setPercentageLevel(80);
        subSkill.setSkillId(101L);

        Mockito.when(subSkillService.getSubSkills()).thenReturn(List.of(subSkill));

        mockMvc.perform(get("/subskills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Go"));
    }

    @Test
    void shouldUpdateSubSkill() throws Exception {
        SubSkillDTO updated = new SubSkillDTO();
        updated.setId(1L);
        updated.setName("C++");
        updated.setPercentageLevel(75);
        updated.setSkillId(102L);

        Mockito.when(subSkillService.updateSubSkill(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(patch("/subskills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("C++"))
                .andExpect(jsonPath("$.percentageLevel").value(75));
    }

    @Test
    void shouldDeleteSubSkill() throws Exception {
        Mockito.when(subSkillService.deleteSubSkill(1L)).thenReturn("Deleted");

        mockMvc.perform(delete("/subskills/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}
