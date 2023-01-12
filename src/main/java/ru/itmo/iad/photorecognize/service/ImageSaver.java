package ru.itmo.iad.photorecognize.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.Dataset;
import ru.itmo.iad.photorecognize.domain.dao.TrainingImageDto;
import ru.itmo.iad.photorecognize.domain.repository.TrainingImageRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class ImageSaver {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    TrainingImageRepository trainingImageRepository;

    public ObjectId saveImage(String userId, BufferedImage image, String imageName) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        InputStream imageStream = new ByteArrayInputStream(os.toByteArray());

        ObjectId fileId = gridFsTemplate.store(imageStream, imageName);

        var trainingImage = TrainingImageDto.builder()
                ._id(ObjectId.get())
                .fileId(fileId)
                .filaName(imageName)
                .dataset(Dataset.test)
                .senderId(userId)
                .dtCreated(new Date())
                .build();

        trainingImageRepository.save(trainingImage);

        return trainingImage.get_id();
    }

}
