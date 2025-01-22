package com.editor.imageeditorapi.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageEditorService {

    public byte[] imageEditor(MultipartFile file, String text, int size) throws IOException {
        BufferedImage inputImage = ImageIO.read(file.getInputStream());

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage editedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = editedImage.createGraphics();

        g2d.drawImage(inputImage, 0, 0, null);

        Font font = new Font("Impact", Font.PLAIN, size);
        Color fontColor = Color.WHITE;
        Color fontBackground = new Color(0, 0, 0, 128);

        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        int x = (width - textWidth) / 2;
        int y = height - textHeight;

        g2d.setColor(fontBackground);
        g2d.fillRect(x - 10, y - metrics.getAscent() - 5, textWidth + 20, textHeight);

        g2d.setColor(fontColor);
        g2d.drawString(text, x, y);

        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(editedImage, "png", outputStream);
        return outputStream.toByteArray();
    }
}
