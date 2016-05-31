package com.ddn.stock.controller;

import com.ddn.stock.service.StockExchangeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenzi on 5/31/2016.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

  @Autowired
  private StockExchangeDataService stockExchangeDataService;

  @RequestMapping("/world")
  public String helloWorld() {
    stockExchangeDataService.getAllHistoricalData("600000.ss");
    return "<h1>hello world</h1>";
  }
}
