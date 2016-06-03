package com.ddn.stock.service.impl;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.service.StockExchangeDataService;
import com.ddn.stock.util.YahooExchangeDataParser;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chenzi on 5/31/2016.
 * <p/>
 * This service reads data from Yahoo API directly
 */
@Service
public class YahooStockExchangeDataServiceImpl implements StockExchangeDataService {

  private static final String BASE_URL = "http://table.finance.yahoo.com/table.csv?s=";

  //slice: "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"
  //all: http://table.finance.yahoo.com/table.csv?s=600000.ss

  @Override
  public Exchange[] getAllHistoricalData(String stockCode) {
    String url = BASE_URL + stockCode;

    try (InputStream inputStream = Request.Get(url).execute().returnContent().asStream()) {
      return YahooExchangeDataParser.parse(stockCode, inputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //Or else return empty data
    return new Exchange[0];
  }
}
