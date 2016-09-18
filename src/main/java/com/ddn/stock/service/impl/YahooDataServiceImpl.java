package com.ddn.stock.service.impl;

import com.ddn.stock.domain.YahooData;
import com.ddn.stock.service.YahooDataService;
import com.ddn.stock.util.YahooExchangeDataParser;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 *
 * This service reads data from Yahoo API directly
 */
@Service
public class YahooDataServiceImpl implements YahooDataService {

  private static final String BASE_URL = "http://table.finance.yahoo.com/table.csv?s=";

  //slice: "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"
  //all: http://table.finance.yahoo.com/table.csv?s=600000.ss

  private List<YahooData> fetch(String stockCode, String url) {
    try (InputStream inputStream = Request.Get(url).execute().returnContent().asStream()) {
      return YahooExchangeDataParser.parse(stockCode, inputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //Or else return empty data
    return Collections.emptyList();
  }

  @Override
  public List<YahooData> getAllHistoricalData(String stockCode) {
    String url = BASE_URL + stockCode;
    return fetch(stockCode, url);
  }

  public List<YahooData> getHistoryBetween(String stockCode, String start, String end) {
    String[] array = start.split("-");
    int c = Integer.parseInt(array[0]);
    int b = Integer.parseInt(array[2]);
    int a = Integer.parseInt(array[1]) - 1;

    array = end.split("-");
    int f = Integer.parseInt(array[0]);
    int e = Integer.parseInt(array[2]);
    int d = Integer.parseInt(array[1]) - 1;

    String url = BASE_URL + stockCode + "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d + "&e=" + e + "&f=" + f;
    return fetch(stockCode, url);
  }
}
