package paf.visa.day26_pafworkshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoDBConfig {
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    private MongoClient client = null;

    @Bean
    public MongoClient mongoClient() {
        if(null == client) client = MongoClients.create(connectionString);
        return client;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "boardgames");
    }
}
