package com.goodamcodes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    private ImageService imageService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        imageService = new ImageService();
        ReflectionTestUtils.setField(imageService, "imageStoragePath", tempDir.toString());
    }

    @Test
    void saveImage_shouldStoreFile(){
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "dummy content".getBytes());
        String savedFileName = imageService.saveImage(mockFile);
        Path savedPath = tempDir.resolve(savedFileName);
        assertTrue(Files.exists(savedPath), "The file should exist at the given path.");
    }

    @Test
    void deleteImage_shouldDeleteIfExists() throws Exception {
        Path testFile = tempDir.resolve("toDelete.jpg");
        Files.write(testFile, "dummy".getBytes());
        imageService.deleteImage("toDelete.jpg");
        assertFalse(Files.exists(testFile), "The file should be deleted.");
    }

    @Test
    void handleFilesUpload_shouldSaveCorrectFile(){
        MockMultipartFile file1 = new MockMultipartFile("file", "image1.jpg", "image/jpeg", "img".getBytes());
        String result = imageService.handleFilesUpload(List.of(file1), 0);
        assertEquals("image1.jpg", result, "The file name should be 'image1.jpg'.");
        assertTrue(Files.exists(tempDir.resolve("image1.jpg")), "The uploaded file should exist.");
    }
}

