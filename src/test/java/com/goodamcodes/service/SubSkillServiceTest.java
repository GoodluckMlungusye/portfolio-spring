package com.goodamcodes.service;

import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.mapper.SubSkillMapper;
import com.goodamcodes.model.Skill;
import com.goodamcodes.model.SubSkill;
import com.goodamcodes.repository.SkillRepository;
import com.goodamcodes.repository.SubSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubSkillServiceTest {

    @Mock
    private SubSkillRepository subSkillRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SubSkillMapper subSkillMapper;

    @InjectMocks
    private SubSkillService subSkillService;

    private SubSkillDTO subSkillDTO;
    private SubSkill subSkill;
    private Skill skill;

    @BeforeEach
    void setUp() {
        skill = new Skill();
        skill.setId(1L);

        subSkillDTO = new SubSkillDTO();
        subSkillDTO.setName("OOP");
        subSkillDTO.setSkillId(1L);

        subSkill = new SubSkill();
        subSkill.setName("OOP");
        subSkill.setSkill(skill);
    }

    @Nested
    @DisplayName("addSubSkill() Tests")
    class AddSubSkillTests {

        @Test
        void shouldAddSubSkill() {
            when(subSkillRepository.findByName("OOP")).thenReturn(Optional.empty());
            when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
            when(subSkillMapper.toSubSkill(subSkillDTO)).thenReturn(subSkill);
            when(subSkillRepository.save(subSkill)).thenReturn(subSkill);
            when(subSkillMapper.toSubSkillDTO(subSkill)).thenReturn(subSkillDTO);

            SubSkillDTO result = subSkillService.addSubSkill(subSkillDTO);

            assertNotNull(result);
            verify(subSkillRepository).save(subSkill);
        }

        @Test
        void shouldThrowWhenSubSkillExists() {
            when(subSkillRepository.findByName("OOP")).thenReturn(Optional.of(subSkill));

            assertThrows(IllegalStateException.class, () -> subSkillService.addSubSkill(subSkillDTO));
        }

        @Test
        void shouldThrowWhenSkillNotFound() {
            when(subSkillRepository.findByName("OOP")).thenReturn(Optional.empty());
            when(skillRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> subSkillService.addSubSkill(subSkillDTO));
        }
    }

    @Nested
    @DisplayName("getSubSkills() Tests")
    class GetSubSkillsTests {

        @Test
        void shouldReturnAllSubSkills() {
            List<SubSkill> subSkills = List.of(subSkill);
            List<SubSkillDTO> subSkillDTOS = List.of(subSkillDTO);

            when(subSkillRepository.findAll()).thenReturn(subSkills);
            when(subSkillMapper.toSubSkillDTOs(subSkills)).thenReturn(subSkillDTOS);

            List<SubSkillDTO> result = subSkillService.getSubSkills();

            assertEquals(1, result.size());
        }
    }

    @Nested
    @DisplayName("updateSubSkill() Tests")
    class UpdateSubSkillTests {

        @Test
        void shouldUpdateSubSkillAndSkill() {
            Skill newSkill = new Skill();
            newSkill.setId(2L);

            subSkillDTO.setSkillId(2L);

            when(subSkillRepository.findById(1L)).thenReturn(Optional.of(subSkill));
            doNothing().when(subSkillMapper).updateSubSkillFromDTO(subSkillDTO, subSkill);
            when(skillRepository.findById(2L)).thenReturn(Optional.of(newSkill));
            when(subSkillRepository.save(subSkill)).thenReturn(subSkill);
            when(subSkillMapper.toSubSkillDTO(subSkill)).thenReturn(subSkillDTO);

            SubSkillDTO result = subSkillService.updateSubSkill(1L, subSkillDTO);

            assertNotNull(result);
            verify(subSkillRepository).save(subSkill);
        }

        @Test
        void shouldThrowWhenSubSkillNotFound() {
            when(subSkillRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> subSkillService.updateSubSkill(1L, subSkillDTO));
        }

        @Test
        void shouldThrowWhenNewSkillNotFound() {
            subSkillDTO.setSkillId(2L);
            when(subSkillRepository.findById(1L)).thenReturn(Optional.of(subSkill));
            when(skillRepository.findById(2L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> subSkillService.updateSubSkill(1L, subSkillDTO));
        }
    }

    @Nested
    @DisplayName("deleteSubSkill() Tests")
    class DeleteSubSkillTests {

        @Test
        void shouldDeleteSubSkill() {
            when(subSkillRepository.existsById(1L)).thenReturn(true);

            String result = subSkillService.deleteSubSkill(1L);

            assertTrue(result.contains("1"));
            verify(subSkillRepository).deleteById(1L);
        }

        @Test
        void shouldThrowWhenSubSkillNotFound() {
            when(subSkillRepository.existsById(1L)).thenReturn(false);

            assertThrows(IllegalStateException.class, () -> subSkillService.deleteSubSkill(1L));
        }
    }
}
