package com.ddn.stock.util;

import com.ddn.stock.domain.Exchange;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzi on 6/1/2016.
 */
public class YahooExchangeDataParser {
  //parse from an InputStream
  public static List<Exchange> parse(String stockCode, InputStream in) throws IOException {
    Iterable<CSVRecord> records = CSVFormat.RFC4180
        .withHeader("Date", "Open", "High", "Low", "Close", "Volume", "Adj Close")
        .withSkipHeaderRecord()
        .parse(new InputStreamReader(in));

    List<Exchange> exchanges = new ArrayList<>();

    for (CSVRecord record : records) {
      String date = record.get("Date");
      float open = Float.parseFloat(record.get("Open"));
      float close = Float.parseFloat(record.get("Close"));
      float high = Float.parseFloat(record.get("High"));
      float low = Float.parseFloat(record.get("Low"));
      float volume = Float.parseFloat(record.get("Volume"));
      //float adjClose = Float.parseFloat(record.get("Adj Close"));
      Exchange exchange = new Exchange(stockCode, date, open, close, high, low, volume);
      exchanges.add(exchange);
    }

    return exchanges;
  }
}
