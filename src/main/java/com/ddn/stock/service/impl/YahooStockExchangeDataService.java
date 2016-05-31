package com.ddn.stock.service.impl;

import com.ddn.stock.domain.StockExchangeData;
import com.ddn.stock.service.StockExchangeDataService;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by chenzi on 5/31/2016.
 *
 * This service reads data from Yahoo API directly
 */
@Service
public class YahooStockExchangeDataService implements StockExchangeDataService {

  private static final String BASE_URL = "http://table.finance.yahoo.com/table.csv?s=";

  //slice: "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"
  //all: http://table.finance.yahoo.com/table.csv?s=600000.ss

  @Override
  public StockExchangeData[] getAllHistoricalData(String stockCode) {
    String url = BASE_URL + stockCode;
    try {
      //String result = Request.Get(url).viaProxy(new HttpHost("proxy-szn", 80)).execute().returnContent().asString();
      HttpHost proxy = new HttpHost("proxy-wtc", 80);
      Executor executor = Executor.newInstance().auth(proxy, "chenzi", "Livqin@140.com").authPreemptive(proxy);
      String result = executor.execute(Request.Get(url)).returnContent().asString();
      System.out.println(result);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return new StockExchangeData[0];
  }
}
