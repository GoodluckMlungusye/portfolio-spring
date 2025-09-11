package com.goodamcodes.service;

import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.mapper.NavigationLinkMapper;
import com.goodamcodes.model.NavigationLink;
import com.goodamcodes.repository.NavigationLinkRepository;
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
class NavigationLinkServiceTest {

    @Mock
    private NavigationLinkRepository navigationLinkRepository;

    @Mock
    private NavigationLinkMapper navigationLinkMapper;

    @InjectMocks
    private NavigationLinkService navigationLinkService;

    private NavigationLinkDTO linkDTO;
    private NavigationLink link;

    @BeforeEach
    void setUp() {
        linkDTO = new NavigationLinkDTO();
        linkDTO.setName("Home");

        link = new NavigationLink();
        link.setName("Home");
    }

    @Nested
    @DisplayName("addNavigationLink() Tests")
    class AddNavigationLinkTests {

        @Test
        void shouldAddNewNavigationLink() {
            when(navigationLinkRepository.findByName("Home")).thenReturn(Optional.empty());
            when(navigationLinkMapper.toNavigationLink(linkDTO)).thenReturn(link);
            when(navigationLinkRepository.save(link)).thenReturn(link);
            when(navigationLinkMapper.toNavigationLinkDTO(link)).thenReturn(linkDTO);

            NavigationLinkDTO result = navigationLinkService.addNavigationLink(linkDTO);

            assertNotNull(result);
            assertEquals("Home", result.getName());
            verify(navigationLinkRepository).save(link);
        }

        @Test
        void shouldThrowIfNavigationLinkAlreadyExists() {
            when(navigationLinkRepository.findByName("Home")).thenReturn(Optional.of(link));

            assertThrows(IllegalStateException.class, () -> navigationLinkService.addNavigationLink(linkDTO));
            verify(navigationLinkRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("addAllNavigationLinks() Tests")
    class AddAllNavigationLinksTests {

        @Test
        void shouldAddOnlyNewNavigationLinks() {
            List<NavigationLinkDTO> dtoList = List.of(linkDTO);
            List<NavigationLink> linkList = List.of(link);

            when(navigationLinkMapper.toNavigationLinks(dtoList)).thenReturn(linkList);
            when(navigationLinkRepository.findByName("Home")).thenReturn(Optional.empty());
            when(navigationLinkRepository.saveAll(linkList)).thenReturn(linkList);
            when(navigationLinkMapper.toNavigationLinkDTOs(linkList)).thenReturn(dtoList);

            List<NavigationLinkDTO> result = navigationLinkService.addAllNavigationLinks(dtoList);

            assertEquals(1, result.size());
            verify(navigationLinkRepository).saveAll(linkList);
        }
    }

    @Nested
    @DisplayName("getNavigationLinks() Tests")
    class GetNavigationLinksTests {

        @Test
        void shouldReturnAllNavigationLinks() {
            List<NavigationLink> links = List.of(link);
            List<NavigationLinkDTO> navigationLinkDTOS = List.of(linkDTO);

            when(navigationLinkRepository.findAll()).thenReturn(links);
            when(navigationLinkMapper.toNavigationLinkDTOs(links)).thenReturn(navigationLinkDTOS);

            List<NavigationLinkDTO> result = navigationLinkService.getNavigationLinks();

            assertEquals(1, result.size());
            assertEquals("Home", result.get(0).getName());
        }
    }

    @Nested
    @DisplayName("updateNavigationLink() Tests")
    class UpdateNavigationLinkTests {

        @Test
        void shouldUpdateExistingNavigationLink() {
            when(navigationLinkRepository.findById(1L)).thenReturn(Optional.of(link));
            doNothing().when(navigationLinkMapper).updateNavigationLinkFromDTO(linkDTO, link);
            when(navigationLinkRepository.save(link)).thenReturn(link);
            when(navigationLinkMapper.toNavigationLinkDTO(link)).thenReturn(linkDTO);

            NavigationLinkDTO result = navigationLinkService.updateNavigationLink(1L, linkDTO);

            assertNotNull(result);
            assertEquals("Home", result.getName());
        }

        @Test
        void shouldThrowIfNavigationLinkDoesNotExist() {
            when(navigationLinkRepository.findById(404L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> navigationLinkService.updateNavigationLink(404L, linkDTO));
        }
    }

    @Nested
    @DisplayName("deleteNavigationLink() Tests")
    class DeleteNavigationLinkTests {

        @Test
        void shouldDeleteNavigationLinkIfExists() {
            when(navigationLinkRepository.existsById(1L)).thenReturn(true);

            String result = navigationLinkService.deleteNavigationLink(1L);

            assertTrue(result.contains("1"));
            verify(navigationLinkRepository).deleteById(1L);
        }

        @Test
        void shouldThrowIfNavigationLinkDoesNotExist() {
            when(navigationLinkRepository.existsById(404L)).thenReturn(false);

            assertThrows(IllegalStateException.class, () -> navigationLinkService.deleteNavigationLink(404L));
            verify(navigationLinkRepository, never()).deleteById(anyLong());
        }
    }
}

