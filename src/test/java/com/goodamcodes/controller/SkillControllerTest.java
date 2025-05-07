package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.SkillDTO;
import com.goodamcodes.service.SkillService;
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

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddSkill() throws Exception {
        SkillDTO skill = new SkillDTO();
        skill.setId(1L);
        skill.setName("Frontend");

        Mockito.when(skillService.addSkill(any())).thenReturn(skill);

        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Frontend"));
    }

    @Test
    void shouldAddAllSkills() throws Exception {
        SkillDTO skill1 = new SkillDTO();
        skill1.setId(1L);
        skill1.setName("Frontend");

        SkillDTO skill2 = new SkillDTO();
        skill2.setId(2L);
        skill2.setName("Backend");

        List<SkillDTO> skillList = List.of(skill1, skill2);

        Mockito.when(skillService.addAllSkills(any())).thenReturn(skillList);

        mockMvc.perform(post("/skills/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldGetSkills() throws Exception {
        SkillDTO skill = new SkillDTO();
        skill.setId(1L);
        skill.setName("DevOps");

        Mockito.when(skillService.getSkills()).thenReturn(List.of(skill));

        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("DevOps"));
    }

    @Test
    void shouldUpdateSkill() throws Exception {
        SkillDTO updated = new SkillDTO();
        updated.setId(1L);
        updated.setName("Updated Skill");

        Mockito.when(skillService.updateSkill(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(patch("/skills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Updated Skill"));
    }

    @Test
    void shouldDeleteSkill() throws Exception {
        Mockito.when(skillService.deleteSkill(1L)).thenReturn("Deleted");

        mockMvc.perform(delete("/skills/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}

