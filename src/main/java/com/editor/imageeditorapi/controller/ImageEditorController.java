package com.editor.imageeditorapi.controller;

import com.editor.imageeditorapi.service.ImageEditorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageEditorController {

    private ImageEditorService imageEditorService;

    public ImageEditorController(ImageEditorService imageEditorService) {
        this.imageEditorService = imageEditorService;
    }

    @PostMapping("/upload-image")
    public ResponseEntity<byte[]> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("text") String text,
            @RequestParam("size") int size) {
        try {
            byte[] imagen = imageEditorService.imageEditor(file, text, size);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=output.png")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imagen);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
