package ru.itmo.iad.photorecognize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageToTensorConvertor {

    @Autowired
    ImageResizer resizer;

    public double[][][] convertImage(InputStream imageStream) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageStream);

        BufferedImage resizedImage = resizer.resizeImage(originalImage, 224, 224);

        double[][][] resultArray = new double[3][224][224];


        for (int i = 0; i < resizedImage.getHeight(); i++) {
            for (int j = 0; j < resizedImage.getWidth(); j++) {
                // TODO: проверить, что не транспонирую
                int rgb = resizedImage.getRGB(j, i);

                resultArray[0][i][j] = ((rgb >> 16) & 0xff) / 255.0;
                resultArray[1][i][j] = ((rgb >> 8) & 0xff) / 255.0;
                resultArray[2][i][j] = ((rgb) & 0xff) / 255.0;
            }
        }

        return resultArray;
    }
}
