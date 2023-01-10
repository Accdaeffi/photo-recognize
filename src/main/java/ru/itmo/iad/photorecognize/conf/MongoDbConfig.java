package ru.itmo.iad.photorecognize.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"ru.itmo.iad.photorecognize.domain"})
public class MongoDbConfig {


    @Value("${spring.data.mongodb.bucket}")
    String bucket;

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDatabaseFactory dbFactory,
                                         MongoConverter converter) {
        return new GridFsTemplate(dbFactory, converter, bucket);
    }


}
