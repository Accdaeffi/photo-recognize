package ru.itmo.iad.photorecognize.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ImageResizer {

    public BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        // crop original image to square aspect ratio
        BufferedImage croppedBI;

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth > originalHeight) {
            int verticalBarWidth = (originalWidth - originalHeight) / 2;
            croppedBI = originalImage.getSubimage(verticalBarWidth, 0, originalHeight, originalHeight);
        } else if (originalHeight > originalWidth) {
            int horizontalBarHeight = (originalHeight - originalWidth) / 2;
            croppedBI = originalImage.getSubimage(0, horizontalBarHeight, originalWidth, originalWidth);
        } else {
            croppedBI = originalImage;
        }

        BufferedImage scaledBI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return scaledBI;
    }
}
