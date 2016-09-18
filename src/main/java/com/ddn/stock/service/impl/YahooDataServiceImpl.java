package com.ddn.stock.service.impl;

import com.ddn.stock.domain.YahooData;
import com.ddn.stock.service.YahooDataService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This service reads data from Yahoo API directly
 */
@Service
public class YahooDataServiceImpl implements YahooDataService {

  private static final String BASE_URL = "http://table.finance.yahoo.com/table.csv?s=";

  //slice: "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"
  //all: http://table.finance.yahoo.com/table.csv?s=600000.ss

  public List<YahooData> parse(String stockCode, InputStream in) throws IOException {
    Iterable<CSVRecord> records = CSVFormat.RFC4180
        .withHeader("Date", "Open", "High", "Low", "Close", "Volume", "Adj Close")
        .withSkipHeaderRecord()
        .parse(new InputStreamReader(in));

    List<YahooData> yahooDatas = new ArrayList<>();

    for (CSVRecord record : records) {
      String date = record.get("Date");
      float open = Float.parseFloat(record.get("Open"));
      float close = Float.parseFloat(record.get("Close"));
      float high = Float.parseFloat(record.get("High"));
      float low = Float.parseFloat(record.get("Low"));
      float volume = Float.parseFloat(record.get("Volume"));
      //float adjClose = Float.parseFloat(record.get("Adj Close"));
      //if volume == 0, then it's not exchanged that day
      if (volume > 0) {
        YahooData yahooData = new YahooData(stockCode, date, open, close, high, low, volume);
        yahooDatas.add(yahooData);
      }
    }

    return yahooDatas;
  }

  private List<YahooData> fetch(String stockCode, String url) {
    try (InputStream inputStream = Request.Get(url).execute().returnContent().asStream()) {
      return parse(stockCode, inputStream);
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
