package com.ddn.stock.integration;

import com.ddn.stock.domain.mongo.History;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(locations = "classpath:application.properties")
public class ApplicationIT {

  @Autowired
  private Datastore datastore;

  @Test
  public void test() {
    assertEquals(1+1,2);
  }

  @Before
  public void before() {
    datastore.delete(datastore.createQuery(History.class));
  }

  @Test
  public void testMorphia() {
    History history = new History();
    datastore.save(history);
    Query<History> query = datastore.createQuery(History.class);

    History h = query.field("date").equal("2016-01-01").get();
    assertNotNull(h);
    assertEquals(10.67,h.getOpen(),0.001);
  }
}
