package com.ddn.stock.indicator;

import com.ddn.stock.domain.YahooData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class KLine {

  private YahooData[] yahooDatas;

  public KLine(List<YahooData> yahooDatas) {
    this.yahooDatas = yahooDatas.toArray(new YahooData[0]);
    Arrays.sort(this.yahooDatas, (YahooData e1 , YahooData e2) -> e1.getDate().compareTo(e2.getDate()));
  }

  public KLine(YahooData[] yahooDatas) {
    this.yahooDatas = yahooDatas;
    Arrays.sort(this.yahooDatas, (YahooData e1 , YahooData e2) -> e1.getDate().compareTo(e2.getDate()));
  }

  public TimeSeries closePriceTimeSeries() {
    TimePoint[] timePoints = Stream.of(yahooDatas)
        .map(exchange -> new TimePoint(exchange.getDate(), exchange.getClose()))
        .toArray(size -> new TimePoint[size]);
    return new TimeSeries(timePoints);
  }

  public int length() {
    return yahooDatas.length;
  }

  public YahooData[] getYahooDatas() {
    return yahooDatas;
  }
}
