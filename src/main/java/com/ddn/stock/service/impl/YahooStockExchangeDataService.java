package com.ddn.stock.service.impl;

import com.ddn.stock.domain.StockExchangeData;
import com.ddn.stock.service.StockExchangeDataService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
      InputStream in = Request.Get(url).execute().returnContent().asStream();
      Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("Date", "Open", "High","Low", "Close", "Volume", "Adj Close")
          .parse(new InputStreamReader(in));
      for (CSVRecord record:records) {
        String date = record.get("Date");
        float open = Float.parseFloat(record.get("Open"));
        float close = Float.parseFloat(record.get("Close"));
        float high = Float.parseFloat(record.get("High"));
        float low = Float.parseFloat(record.get("Low"));
        float volume = Float.parseFloat(record.get("Volume"));
        float adjClose = Float.parseFloat(record.get("Adj Close"));
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return new StockExchangeData[0];
  }
}
