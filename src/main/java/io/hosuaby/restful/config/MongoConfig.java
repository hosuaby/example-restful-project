package io.hosuaby.restful.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Configuration of Spring Data MongoDB.
 */
@Configuration
@EnableMongoRepositories("io.hosuaby.restful.repositories")
public class MongoConfig extends AbstractMongoConfiguration {

    /** Host of MongoDB server */
    @Value("#{ environment.DB_HOST }")
    private String host;

    /** Port of MongoDB server */
    @Value("#{ environment.DB_PORT }")
    private int port;

    /** Username for MongoDB server */
    @Value("#{ environment.DB_USERNAME }")
    private String username;

    /** Password for MongoDB server */
    @Value("#{ environment.DB_PASSWORD }")
    private String password = "";

    /** DB name */
    @Value("#{ environment.DB_NAME }")
    private String dbName;

    /** {@inheritDoc} */
    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    /** {@inheritDoc} */
    @Override
    public Mongo mongo() throws Exception {
        if (username != null && password != null) {
            MongoCredential credential = MongoCredential
                    .createCredential(username, getDatabaseName(),
                            password.toCharArray());

            return new MongoClient(new ServerAddress(host, port),
                    new ArrayList<MongoCredential>() {
                private static final long serialVersionUID = 1L;
                {
                    add(credential);
                }
            });
        } else {
            return new MongoClient(new ServerAddress(host, port));
        }
    }

    /** {@inheritDoc} */
    @Override
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory((MongoClient) mongo(), getDatabaseName());
    }

}
