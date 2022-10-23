package ru.itmo.iad.photorecognize.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = { "ru.itmo.iad.photorecognize.domain" })
public class MongoDbConfig {

}
