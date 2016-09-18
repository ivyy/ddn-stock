package com.ddn.stock.integration;

import com.ddn.stock.domain.mongo.History;
import com.ddn.stock.service.ImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(locations = "classpath:application.properties")
public class ImportServiceIT {

  @Autowired
  private ImportService importService;

  @Autowired
  private Datastore datastore;

  @Test
  public void testFromYahoo() {
    String code = "600000.ss";
    importService.fromYahoo("code");
    long count = datastore.getCount(datastore.createQuery(History.class).field("code").equal(code));
    assertTrue(count > 0);
  }

  @Test
  public void testFromYahooBetween() {
    String code = "600116.ss";
    importService.fromYahoo(code, "2016-08-01","2016-09-01");
    long count = datastore.getCount(datastore.createQuery(History.class).field("code").equal(code));
    assertTrue(count > 0);
  }
}
