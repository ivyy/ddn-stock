package com.ddn.stock.controller;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.domain.Stock;
import com.ddn.stock.service.StockExchangeDataImportService;
import com.ddn.stock.strategy.impl.SimpleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;


@RestController
@RequestMapping("/hello")
public class HelloController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private StockExchangeDataImportService stockExchangeDataImportService;

  @RequestMapping(value = "/world", method = RequestMethod.GET)
  public String helloWorld() {
    Stock s = mongoTemplate.findOne(query(where("code").is("600000")), Stock.class);
    if (s == null) {
      Stock stock = new Stock("600000", "ss", "600000.ss", "PU FA BANK");
      mongoTemplate.insert(stock);
    } else {
      System.out.println(s);
      mongoTemplate.updateFirst(query(where("code").is("600000")), update("description", "PU FA BANK(UPDATE)"), Stock.class);
      s = mongoTemplate.findOne(query(where("code").is("600000")), Stock.class);
      System.out.println(s);
      mongoTemplate.remove(s);
    }
    return "<h1>hello world</h1>";
  }

  @RequestMapping(value = "/import/yahoo", method = RequestMethod.GET)
  public void importData() {
    stockExchangeDataImportService.fromYahoo();
  }

  @RequestMapping(value = "/import/csv", method = RequestMethod.GET)
  public void importCsv() {
    stockExchangeDataImportService.fromLocalCSV();
  }

  @RequestMapping(value = "/analyze/{stockCode:.+}", method = RequestMethod.GET)
  public void analyze(@PathVariable String stockCode) {
    Exchange[] exchanges = mongoTemplate.find(query(where("stockCode").is(stockCode)), Exchange.class).toArray(new Exchange[0]);
    SimpleStrategy simpleStrategy = new SimpleStrategy(stockCode, exchanges);
    simpleStrategy.apply();
  }
}
