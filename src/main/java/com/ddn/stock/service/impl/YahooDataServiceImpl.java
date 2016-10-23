package com.ddn.stock.service.impl;

import com.ddn.stock.domain.TickPeriod;
import com.ddn.stock.domain.document.Tick;
import com.ddn.stock.service.YahooDataService;
import com.ddn.stock.util.CommonUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This service reads data from Yahoo API directly
 */
@Service
public class YahooDataServiceImpl implements YahooDataService {

  private static final String BASE_URL = "http://table.finance.yahoo.com/table.csv?s=";

  //slice: "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"
  //all: http://table.finance.yahoo.com/table.csv?s=600000.ss

  private List<Tick> parse(String stockCode, InputStream in) throws IOException, ParseException {
    Iterable<CSVRecord> records = CSVFormat.RFC4180
        .withHeader("Date", "Open", "High", "Low", "Close", "Volume", "Adj Close")
        .withSkipHeaderRecord()
        .parse(new InputStreamReader(in));

    List<Tick> yahooDatas = new ArrayList<>();

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
        Tick yahooData = new Tick(null, stockCode, new SimpleDateFormat("yyyy-MM-dd").parse(date),
            open, high, low, close, volume, TickPeriod.DAILY);
        yahooDatas.add(yahooData);
      }
    }

    return yahooDatas;
  }

  private List<Tick> fetch(String stockCode, String url) {
    try (InputStream inputStream = Request.Get(url).execute().returnContent().asStream()) {
      return parse(stockCode, inputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //Or else return empty data
    return Collections.emptyList();
  }

  @Override
  public List<Tick> getAll(String stockCode) {
    String url = BASE_URL + CommonUtil.toYahooFormat(stockCode);
    return fetch(stockCode, url);
  }

  public List<Tick> getFrom(String stockCode, LocalDate startDate) {
    int a = startDate.getMonthValue() - 1;
    int b = startDate.getDayOfMonth();
    int c = startDate.getYear();

    String url = BASE_URL + CommonUtil.toYahooFormat(stockCode) + "&a=" + a + "&b=" + b + "&c=" + c;
    return fetch(stockCode, url);
  }

}
