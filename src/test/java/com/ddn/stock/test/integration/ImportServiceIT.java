package com.ddn.stock.test.integration;

import com.ddn.stock.domain.document.PowerFactor;
import com.ddn.stock.domain.document.Stock;
import com.ddn.stock.domain.document.Tick;
import com.ddn.stock.service.ImportService;
import com.ddn.stock.service.StockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class ImportServiceIT {

  @Autowired
  private ImportService importService;

  @Autowired
  private StockService stockService;

  @Autowired
  private Datastore datastore;

  @Before
  public void before() {
    datastore.delete(datastore.createQuery(Tick.class));
    datastore.delete(datastore.createQuery(PowerFactor.class));
    datastore.delete(datastore.createQuery(Stock.class));

    assertTrue(datastore.find(Tick.class).countAll() == 0);
    assertTrue(datastore.find(PowerFactor.class).countAll() == 0);
    assertTrue(datastore.find(Stock.class).countAll() == 0);

    //first create a stock
    stockService.saveOrUpdate(Stock.of(null, "600000", null, "浦发银行", "浦发银行"));
  }

  @Test
  public void testFromYahoo() {
    String code = "600000";
    importService.importDailyTicksFromYahoo(code);
    long count = datastore.getCount(datastore.createQuery(Tick.class).field("stockCode").equal(code));
    assertTrue(count > 0);
  }

  @Test
  public void testFromYahooBetween() {
    String code = "600000";
    importService.importDailyTicksFromYahoo(code, LocalDate.parse("2010-08-01"));
    long count = datastore.getCount(datastore.createQuery(Tick.class).field("stockCode").equal(code));
    assertTrue(count > 0);
  }

  @Test
  public void testPowerFactorFromSina() {
    String stockCode = "600000";
    importService.importPowerFactorFromSina(stockCode, 2016,3);
  }

  @Test
  public void testImportFromScratch() {
    //import all its data
    importService.importFromScratch();
  }
}
