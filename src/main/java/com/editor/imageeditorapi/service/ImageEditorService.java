package com.editor.imageeditorapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
        import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImageEditorService {

    private final Map<String, byte[]> imageCache = new ConcurrentHashMap<>();

    public byte[] imageEditor(MultipartFile file, String text, int size) throws IOException {

        String imageKey = file.getOriginalFilename() + "_" + text + "_" + size;

        byte[] cachedImage = getCachedImage(imageKey);
        if (cachedImage != null) {
            return cachedImage;
        }

        BufferedImage inputImage = ImageIO.read(file.getInputStream());

        int targetWidth = Math.min(inputImage.getWidth(), 1024);
        int targetHeight = (inputImage.getHeight() * targetWidth) / inputImage.getWidth();
        inputImage = resizeImage(inputImage, targetWidth, targetHeight);

        Graphics2D g2d = inputImage.createGraphics();

        g2d.drawImage(inputImage, 0, 0, null);

        Font font = new Font("Impact", Font.PLAIN, size);
        Color fontColor = Color.WHITE;
        Color fontBackground = new Color(0, 0, 0, 128);

        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        int x = (inputImage.getWidth() - textWidth) / 2;
        int y = inputImage.getHeight() - textHeight;

        g2d.setColor(fontBackground);
        g2d.fillRect(x - 10, y - metrics.getAscent() - 5, textWidth + 20, textHeight);

        g2d.setColor(fontColor);
        g2d.drawString(text, x, y);

        g2d.dispose();

        byte[] optimizedImage = compressImage(inputImage, 0.8f);

        cacheImage(imageKey, optimizedImage);

        return optimizedImage;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image resultingImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    private byte[] compressImage(BufferedImage image, float quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();
        ios.close();
        return outputStream.toByteArray();
    }

    private byte[] getCachedImage(String imageKey) {
        return imageCache.get(imageKey);
    }

    private void cacheImage(String imageKey, byte[] imageData) {
        imageCache.put(imageKey, imageData);
    }
}

