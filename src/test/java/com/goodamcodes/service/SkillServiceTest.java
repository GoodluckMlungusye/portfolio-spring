package com.goodamcodes.service;

import com.goodamcodes.dto.SkillDTO;
import com.goodamcodes.mapper.SkillMapper;
import com.goodamcodes.model.Skill;
import com.goodamcodes.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillMapper skillMapper;

    @InjectMocks
    private SkillService skillService;

    private SkillDTO skillDTO;
    private Skill skill;

    @BeforeEach
    void setUp() {
        skillDTO = new SkillDTO();
        skillDTO.setName("Java");

        skill = new Skill();
        skill.setName("Java");
    }

    @Nested
    @DisplayName("addSkill() Tests")
    class AddSkillTests {

        @Test
        void shouldAddNewSkill() {
            when(skillRepository.findByName("Java")).thenReturn(Optional.empty());
            when(skillMapper.toSkill(skillDTO)).thenReturn(skill);
            when(skillRepository.save(skill)).thenReturn(skill);
            when(skillMapper.toSkillDTO(skill)).thenReturn(skillDTO);

            SkillDTO result = skillService.addSkill(skillDTO);

            assertNotNull(result);
            assertEquals("Java", result.getName());
            verify(skillRepository).save(skill);
        }

        @Test
        void shouldThrowIfSkillExists() {
            when(skillRepository.findByName("Java")).thenReturn(Optional.of(skill));

            assertThrows(IllegalStateException.class, () -> skillService.addSkill(skillDTO));
            verify(skillRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("addAllSkills() Tests")
    class AddAllSkillsTests {

        @Test
        void shouldAddOnlyNewSkills() {
            List<SkillDTO> dtoList = List.of(skillDTO);
            List<Skill> skillList = List.of(skill);

            when(skillMapper.toSkills(dtoList)).thenReturn(skillList);
            when(skillRepository.findByName("Java")).thenReturn(Optional.empty());
            when(skillRepository.saveAll(skillList)).thenReturn(skillList);
            when(skillMapper.toSkillDTOs(skillList)).thenReturn(dtoList);

            List<SkillDTO> result = skillService.addAllSkills(dtoList);

            assertEquals(1, result.size());
            verify(skillRepository).saveAll(skillList);
        }
    }

    @Nested
    @DisplayName("getSkills() Tests")
    class GetSkillsTests {

        @Test
        void shouldReturnAllSkills() {
            List<Skill> skills = List.of(skill);
            List<SkillDTO> skillDTOS = List.of(skillDTO);

            when(skillRepository.findAllWithSubSkills()).thenReturn(skills);
            when(skillMapper.toSkillDTOs(skills)).thenReturn(skillDTOS);

            List<SkillDTO> result = skillService.getSkills();

            assertEquals(1, result.size());
            assertEquals("Java", result.getFirst().getName());
        }
    }

    @Nested
    @DisplayName("updateSkill() Tests")
    class UpdateSkillTests {

        @Test
        void shouldUpdateSkill() {
            when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
            doNothing().when(skillMapper).updateSkillFromDTO(skillDTO, skill);
            when(skillRepository.save(skill)).thenReturn(skill);
            when(skillMapper.toSkillDTO(skill)).thenReturn(skillDTO);

            SkillDTO result = skillService.updateSkill(1L, skillDTO);

            assertNotNull(result);
            verify(skillRepository).save(skill);
        }

        @Test
        void shouldThrowIfSkillNotFound() {
            when(skillRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> skillService.updateSkill(999L, skillDTO));
        }
    }

    @Nested
    @DisplayName("deleteSkill() Tests")
    class DeleteSkillTests {

        @Test
        void shouldDeleteSkill() {
            when(skillRepository.existsById(1L)).thenReturn(true);

            String result = skillService.deleteSkill(1L);

            assertTrue(result.contains("1"));
            verify(skillRepository).deleteById(1L);
        }

        @Test
        void shouldThrowIfSkillNotFound() {
            when(skillRepository.existsById(999L)).thenReturn(false);

            assertThrows(IllegalStateException.class, () -> skillService.deleteSkill(999L));
        }
    }
}

