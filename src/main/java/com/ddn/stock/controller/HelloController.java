package com.ddn.stock.controller;

import com.ddn.stock.domain.Stock;
import com.ddn.stock.service.StockExchangeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;


@RestController
@RequestMapping("/hello")
public class HelloController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @RequestMapping("/world")
  public String helloWorld() {
    //Stock stock = mongoTemplate.find(Query.query())
    Stock s = mongoTemplate.findOne(query(where("code").is("600000")), Stock.class);
    if (s == null) {
      Stock stock = new Stock("600000", "ss", "600000.ss", "PU FA BANK");
      mongoTemplate.insert(stock);
    } else {
      System.out.println(s);
      mongoTemplate.updateFirst(query(where("code").is("600000")),update("description","PU FA BANK(UPDATE)"), Stock.class);
      s = mongoTemplate.findOne(query(where("code").is("600000")), Stock.class);
      System.out.println(s);
      mongoTemplate.remove(s);
    }
    return "<h1>hello world</h1>";
  }
}
