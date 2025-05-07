package com.goodamcodes.service;

import com.goodamcodes.dto.ServiceOfferedDTO;
import com.goodamcodes.mapper.ServiceOfferedMapper;
import com.goodamcodes.model.ServiceOffered;
import com.goodamcodes.repository.ServiceOfferedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceOfferedServiceTest {

    @Mock
    private ServiceOfferedRepository serviceOfferedRepository;

    @Mock
    private ServiceOfferedMapper serviceOfferedMapper;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ServiceOfferedService serviceOfferedService;

    private ServiceOfferedDTO serviceDTO;
    private ServiceOffered service;

    @BeforeEach
    void setUp() {
        serviceDTO = new ServiceOfferedDTO();
        serviceDTO.setName("Web Development");

        service = new ServiceOffered();
        service.setName("Web Development");
    }

    @Nested
    @DisplayName("addServiceOffered() Tests")
    class AddServiceOfferedTests {

        @Test
        void shouldAddNewServiceOffered() {
            MultipartFile file = mock(MultipartFile.class);
            when(serviceOfferedRepository.findByName("Web Development")).thenReturn(Optional.empty());
            when(serviceOfferedMapper.toServiceOffered(serviceDTO)).thenReturn(service);
            when(imageService.saveImage(file)).thenReturn("webdev.jpg");
            when(serviceOfferedRepository.save(service)).thenReturn(service);
            when(serviceOfferedMapper.toServiceOfferedDTO(service)).thenReturn(serviceDTO);

            ServiceOfferedDTO result = serviceOfferedService.addServiceOffered(serviceDTO, file);

            assertNotNull(result);
            assertEquals("Web Development", result.getName());
            assertEquals("webdev.jpg", service.getImage());
            verify(serviceOfferedRepository).save(service);
        }

        @Test
        void shouldThrowIfServiceOfferedExists() {
            when(serviceOfferedRepository.findByName("Web Development")).thenReturn(Optional.of(service));

            assertThrows(IllegalStateException.class, () -> serviceOfferedService.addServiceOffered(serviceDTO, null));
            verify(serviceOfferedRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("addAllServicesOffered() Tests")
    class AddAllServicesOfferedTests {

        @Test
        void shouldAddOnlyNewServicesOffered() {
            List<ServiceOfferedDTO> dtoList = List.of(serviceDTO);
            List<ServiceOffered> serviceList = List.of(service);
            MultipartFile file = mock(MultipartFile.class);

            when(serviceOfferedMapper.toServicesOffered(dtoList)).thenReturn(serviceList);
            when(serviceOfferedRepository.findByName("Web Development")).thenReturn(Optional.empty());
            when(imageService.handleFilesUpload(List.of(file), 0)).thenReturn("webdev.jpg");
            when(serviceOfferedRepository.saveAll(serviceList)).thenReturn(serviceList);
            when(serviceOfferedMapper.toServiceOfferedDTOs(serviceList)).thenReturn(dtoList);

            List<ServiceOfferedDTO> result = serviceOfferedService.addAllServicesOffered(dtoList, List.of(file));

            assertEquals(1, result.size());
            verify(serviceOfferedRepository).saveAll(serviceList);
        }
    }

    @Nested
    @DisplayName("getServiceOfferedList() Tests")
    class GetServiceOfferedListTests {

        @Test
        void shouldReturnAllServiceOffered() {
            List<ServiceOffered> serviceList = List.of(service);
            List<ServiceOfferedDTO> dtoList = List.of(serviceDTO);

            when(serviceOfferedRepository.findAll()).thenReturn(serviceList);
            when(serviceOfferedMapper.toServiceOfferedDTOs(serviceList)).thenReturn(dtoList);

            List<ServiceOfferedDTO> result = serviceOfferedService.getServiceOfferedList();

            assertEquals(1, result.size());
            assertEquals("Web Development", result.get(0).getName());
        }
    }

    @Nested
    @DisplayName("updateServiceOffered() Tests")
    class UpdateServiceOfferedTests {

        @Test
        void shouldUpdateExistingServiceOffered() {
            MultipartFile file = mock(MultipartFile.class);
            service.setImage("old.jpg");

            when(serviceOfferedRepository.findById(1L)).thenReturn(Optional.of(service));
            doNothing().when(imageService).deleteImage("old.jpg");
            when(imageService.saveImage(file)).thenReturn("webdev_updated.jpg");
            doNothing().when(serviceOfferedMapper).updateServiceOfferedFromDTO(serviceDTO, service);
            when(serviceOfferedRepository.save(service)).thenReturn(service);
            when(serviceOfferedMapper.toServiceOfferedDTO(service)).thenReturn(serviceDTO);

            ServiceOfferedDTO result = serviceOfferedService.updateServiceOffered(1L, serviceDTO, file);

            assertNotNull(result);
            assertEquals("webdev_updated.jpg", service.getImage());
            verify(imageService).deleteImage("old.jpg");
            verify(serviceOfferedRepository).save(service);
        }

        @Test
        void shouldThrowIfServiceOfferedDoesNotExist() {
            when(serviceOfferedRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> serviceOfferedService.updateServiceOffered(999L, serviceDTO, null));
        }
    }

    @Nested
    @DisplayName("deleteServiceOffered() Tests")
    class DeleteServiceOfferedTests {

        @Test
        void shouldDeleteServiceOffered() {
            service.setImage("webdev.jpg");

            when(serviceOfferedRepository.findById(1L)).thenReturn(Optional.of(service));
            doNothing().when(imageService).deleteImage("webdev.jpg");

            String result = serviceOfferedService.deleteServiceOffered(1L);

            assertTrue(result.contains("1"));
            verify(serviceOfferedRepository).deleteById(1L);
            verify(imageService).deleteImage("webdev.jpg");
        }

        @Test
        void shouldThrowIfServiceOfferedDoesNotExist() {
            when(serviceOfferedRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> serviceOfferedService.deleteServiceOffered(999L));
            verify(serviceOfferedRepository, never()).deleteById(any());
        }
    }
}
