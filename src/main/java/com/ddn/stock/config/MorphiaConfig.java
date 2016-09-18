package com.ddn.stock.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MorphiaConfig {

  @Value("${mongodb.host}")
  private String mongodbHost;

  @Value("${mongodb.database}")
  private String database;

  @Bean
  public Datastore morphiaDataStore() {
    final Morphia morphia = new Morphia();
    morphia.mapPackage("com.ddn.stock.domain.mongo");
    final Datastore datastore = morphia.createDatastore(new MongoClient(mongodbHost), database);
    //call explicitly to ensure indexes
    datastore.ensureIndexes();

    return datastore;
  }
}
