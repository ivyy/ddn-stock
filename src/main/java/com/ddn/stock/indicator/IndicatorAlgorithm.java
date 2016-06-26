package com.ddn.stock.indicator;

import com.ddn.stock.domain.*;
import org.springframework.stereotype.Component;

@Component
public class IndicatorAlgorithm {

  public KDJ kdj(KLine kLine) {
    int n = 9;
    int length = kLine.length();
    TimePoint[] rsvPoints = new TimePoint[length];

    //calculate RSV
    TimeSeries closePriceTimeSeries = kLine.closePriceTimeSeries();
    Exchange[] exchanges = kLine.getExchanges();
    rsvPoints[0] = new TimePoint(exchanges[0].getDate(), 100);
    rsvPoints[1] = new TimePoint(exchanges[1].getDate(), 100);
    for (int i = 2; i < length; i++) {
      double close = exchanges[i].getClose();
      double highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
      for (int j = 0; j < n && i - j >= 0; j++) {
        double high = exchanges[i - j].getHigh();
        double low = exchanges[i - j].getLow();
        if (high > highest) highest = high;
        if (low < lowest) lowest = low;
      }
      rsvPoints[i] = new TimePoint(exchanges[i].getDate(), (close - lowest) / (highest - lowest) * 100);
    }

    TimePoint[] kPoints = new TimePoint[length];
    TimePoint[] dPoints = new TimePoint[length];
    TimePoint[] jPoints = new TimePoint[length];

    kPoints[0] = new TimePoint(exchanges[0].getDate(), 100);
    kPoints[1] = new TimePoint(exchanges[1].getDate(), 100);
    dPoints[0] = new TimePoint(exchanges[0].getDate(), 100);
    dPoints[1] = new TimePoint(exchanges[1].getDate(), 100);
    jPoints[0] = new TimePoint(exchanges[0].getDate(), 100);
    jPoints[1] = new TimePoint(exchanges[1].getDate(), 100);

    for (int i = 2; i < length; i++) {
      double lastK = kPoints[i - 1].getValue();
      double lastD = dPoints[i - 1].getValue();

      kPoints[i] = new TimePoint(exchanges[i].getDate(), 2.0 / 3 * lastK + 1.0 / 3 * rsvPoints[i].getValue());
      dPoints[i] = new TimePoint(exchanges[i].getDate(), 2.0 / 3 * lastD + 1.0 / 3 * kPoints[i].getValue());
      jPoints[i] = new TimePoint(exchanges[i].getDate(), 3 * kPoints[i].getValue() - 2 * dPoints[i].getValue());
    }

    return new KDJ(new TimeSeries(kPoints), new TimeSeries(dPoints), new TimeSeries(jPoints));
  }
}
