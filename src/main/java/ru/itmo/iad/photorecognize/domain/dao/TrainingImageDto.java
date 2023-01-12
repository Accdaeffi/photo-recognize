package ru.itmo.iad.photorecognize.domain.dao;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.itmo.iad.photorecognize.domain.Dataset;

import java.util.Date;

@Document(collection = "training_image")
@Data
@Builder
public class TrainingImageDto {

    @Id
    @Field
    private ObjectId _id;

    @Field
    private ObjectId fileId;

    @Field
    private String fileName;

    @Field
    private String senderId;

    @Field
    private Date dtCreated;

    @Field
    private Dataset dataset;
}
