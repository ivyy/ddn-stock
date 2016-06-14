package com.ddn.stock.domain;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class KLine {
  private Exchange[] exchanges;

  public KLine(List<Exchange> exchanges) {
    this.exchanges = exchanges.toArray(new Exchange[0]);
    Arrays.sort(this.exchanges, (Exchange e1 ,Exchange e2) -> e1.getDate().compareTo(e2.getDate()));
  }

  public KLine(Exchange[] exchanges) {
    this.exchanges = exchanges;
    Arrays.sort(this.exchanges, (Exchange e1 ,Exchange e2) -> e1.getDate().compareTo(e2.getDate()));
  }

  public TimeSeries closePriceTimeSeries() {
    DataPoint[] dataPoints = Stream.of(exchanges)
        .map(exchange -> new DataPoint(exchange.getDate(), exchange.getClose()))
        .toArray(size -> new DataPoint[size]);
    return new TimeSeries(dataPoints);
  }

  public int length() {
    return exchanges.length;
  }

  public Exchange[] getExchanges() {
    return exchanges;
  }
}
