package com.goodamcodes.service;

import com.goodamcodes.dto.ExploreDTO;
import com.goodamcodes.mapper.ExploreMapper;
import com.goodamcodes.model.Explore;
import com.goodamcodes.repository.ExploreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExploreServiceTest {

    @Mock
    private ExploreRepository exploreRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private ExploreMapper exploreMapper;

    @InjectMocks
    private ExploreService exploreService;

    private ExploreDTO exploreDTO;
    private Explore explore;

    @BeforeEach
    void setup() {
        exploreDTO = new ExploreDTO();
        exploreDTO.setDescription("Explore Item");

        explore = new Explore();
        explore.setDescription("Explore Item");
    }

    @Nested
    @DisplayName("addExplore() Tests")
    class AddExploreTests {

        @Test
        void shouldAddNewExploreWithImage() {
            MultipartFile file = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test-data".getBytes());

            when(exploreRepository.findByDescription("Explore Item")).thenReturn(Optional.empty());
            when(exploreMapper.toExplore(exploreDTO)).thenReturn(explore);
            when(imageService.saveImage(file)).thenReturn("image.jpg");
            when(exploreRepository.save(explore)).thenReturn(explore);
            when(exploreMapper.toExploreDTO(explore)).thenReturn(exploreDTO);

            ExploreDTO result = exploreService.addExplore(exploreDTO, file);

            assertNotNull(result);
            assertEquals("image.jpg", explore.getImage());
            verify(imageService).saveImage(file);
            verify(exploreRepository).save(explore);
        }

        @Test
        void shouldThrowIfExploreExists() {
            when(exploreRepository.findByDescription("Explore Item")).thenReturn(Optional.of(explore));

            assertThrows(IllegalStateException.class, () -> exploreService.addExplore(exploreDTO, null));
            verify(exploreRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("addAllExplores() Tests")
    class AddAllExploresTests {

        @Test
        void shouldAddOnlyNewExplores() {
            List<ExploreDTO> dtoList = List.of(exploreDTO);
            List<Explore> exploreList = List.of(explore);
            MultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "test".getBytes());

            when(exploreRepository.findByDescription("Explore Item")).thenReturn(Optional.empty());
            when(exploreMapper.toExplores(dtoList)).thenReturn(exploreList);
            when(imageService.handleFilesUpload(List.of(file), 0)).thenReturn("image.jpg");
            when(exploreRepository.saveAll(exploreList)).thenReturn(exploreList);
            when(exploreMapper.toExploreDTOs(exploreList)).thenReturn(dtoList);

            List<ExploreDTO> result = exploreService.addAllExplores(dtoList, List.of(file));

            assertEquals(1, result.size());
            verify(exploreRepository).saveAll(exploreList);
        }
    }

    @Nested
    @DisplayName("getExploreList() Tests")
    class GetExploreListTests {

        @Test
        void shouldReturnExploreDTOs() {
            List<Explore> exploreList = List.of(explore);
            List<ExploreDTO> dtoList = List.of(exploreDTO);

            when(exploreRepository.findAll()).thenReturn(exploreList);
            when(exploreMapper.toExploreDTOs(exploreList)).thenReturn(dtoList);

            List<ExploreDTO> result = exploreService.getExploreList();

            assertEquals(1, result.size());
            verify(exploreRepository).findAll();
            verify(exploreMapper).toExploreDTOs(exploreList);
        }
    }

    @Nested
    @DisplayName("updateExplore() Tests")
    class UpdateExploreTests {

        @Test
        void shouldUpdateExistingExplore() {
            MultipartFile file = new MockMultipartFile("file", "new.jpg", "image/jpeg", "test".getBytes());
            explore.setImage("old.jpg");

            when(exploreRepository.findById(1L)).thenReturn(Optional.of(explore));
            doNothing().when(imageService).deleteImage("old.jpg");
            when(imageService.saveImage(file)).thenReturn("new.jpg");
            doNothing().when(exploreMapper).updateExploreFromDTO(exploreDTO, explore);
            when(exploreRepository.save(explore)).thenReturn(explore);
            when(exploreMapper.toExploreDTO(explore)).thenReturn(exploreDTO);

            ExploreDTO result = exploreService.updateExplore(1L, exploreDTO, file);

            assertNotNull(result);
            assertEquals("new.jpg", explore.getImage());
            verify(imageService).deleteImage("old.jpg");
            verify(imageService).saveImage(file);
        }

        @Test
        void shouldThrowIfExploreNotFound() {
            when(exploreRepository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> exploreService.updateExplore(99L, exploreDTO, null));
        }
    }

    @Nested
    @DisplayName("deleteExplore() Tests")
    class DeleteExploreTests {

        @Test
        void shouldDeleteExploreIfExists() {
            explore.setImage("delete.jpg");

            when(exploreRepository.findById(1L)).thenReturn(Optional.of(explore));
            doNothing().when(imageService).deleteImage("delete.jpg");

            String result = exploreService.deleteExplore(1L);

            assertTrue(result.contains("1"));
            verify(imageService).deleteImage("delete.jpg");
            verify(exploreRepository).deleteById(1L);
        }

        @Test
        void shouldThrowIfExploreNotExists() {
            when(exploreRepository.findById(404L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> exploreService.deleteExplore(404L));
            verify(exploreRepository, never()).deleteById(anyLong());
        }
    }
}

