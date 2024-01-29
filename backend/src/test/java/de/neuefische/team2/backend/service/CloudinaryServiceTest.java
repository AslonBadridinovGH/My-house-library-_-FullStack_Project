package de.neuefische.team2.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.client.methods.RequestBuilder.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CloudinaryServiceTest {
    private Cloudinary cloudinary = mock(Cloudinary.class);
    private Uploader uploader = mock(Uploader.class);
    private CloudinaryService cloudinaryService = new CloudinaryService(cloudinary);

    @Test
    void uploadFileTest() throws Exception {
//GIVEN
        Map map =new HashMap();
        map.put("secure_url", "url");
        MockMultipartFile file = new MockMultipartFile(
                "file", "Datei.jpg", MediaType.IMAGE_JPEG_VALUE, "Hello, World!".getBytes());
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(),any())).thenReturn(map);
        //WHEN
        String urlExpected = cloudinaryService.uploadFile(file);
        //THEN
        assertEquals("url", urlExpected);
    }
}