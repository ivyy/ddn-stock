package com.ddn.stock.indicator;

import com.ddn.stock.domain.Exchange;

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
    TimePoint[] timePoints = Stream.of(exchanges)
        .map(exchange -> new TimePoint(exchange.getDate(), exchange.getClose()))
        .toArray(size -> new TimePoint[size]);
    return new TimeSeries(timePoints);
  }

  public int length() {
    return exchanges.length;
  }

  public Exchange[] getExchanges() {
    return exchanges;
  }
}
