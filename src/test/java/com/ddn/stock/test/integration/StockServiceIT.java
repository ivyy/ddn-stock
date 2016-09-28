package com.ddn.stock.test.integration;

import com.ddn.stock.domain.mongo.Stock;
import com.ddn.stock.service.StockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(locations = "classpath:application.properties")
public class StockServiceIT {

  @Autowired
  private StockService stockService;

  @Before
  public void before() {
    stockService.deleteAll();

    String code = "600116.XSHG";
    Stock stock = new Stock(null, code, "三峡水利", "水利板块龙头");
    stockService.saveOrUpdate(stock);
  }

  @Test
  public void testFindOne() {
    Stock stock = stockService.findOne("600116.XSHG");
    assertNotNull(stock);
    assertEquals("水利板块龙头", stock.getDescription());
  }

  @Test
  public void testUpdate() {
    Stock stock = stockService.findOne("600116.XSHG");
    stock.setDescription("test");
    stockService.saveOrUpdate(stock);
    stock = stockService.findOne("600116.XSHG");
    assertEquals("test", stock.getDescription());
  }

  @Test
  public void testDelete() {
    stockService.delete("600116.XSHG");
    assertNull(stockService.findOne("600116.XSHG"));
  }

}
