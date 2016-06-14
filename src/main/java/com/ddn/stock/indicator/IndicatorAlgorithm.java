package com.ddn.stock.indicator;

import com.ddn.stock.domain.*;
import org.springframework.stereotype.Component;

@Component
public class IndicatorAlgorithm {

  public KDJ kdj(KLine kLine) {
    int n = 9;
    int length = kLine.length();
    DataPoint[] rsvPoints = new DataPoint[length];

    //calculate RSV
    TimeSeries closePriceTimeSeries = kLine.closePriceTimeSeries();
    Exchange[] exchanges = kLine.getExchanges();
    rsvPoints[0] = new DataPoint(exchanges[0].getDate(), 100);
    rsvPoints[1] = new DataPoint(exchanges[1].getDate(), 100);
    for (int i = 2; i < length; i++) {
      double close = exchanges[i].getClose();
      double highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
      for (int j = 0; j < n && i - j >= 0; j++) {
        double high = exchanges[i - j].getHigh();
        double low = exchanges[i - j].getLow();
        if (high > highest) highest = high;
        if (low < lowest) lowest = low;
      }
      rsvPoints[i] = new DataPoint(exchanges[i].getDate(), (close - lowest) / (highest - lowest) * 100);
    }

    DataPoint[] kPoints = new DataPoint[length];
    DataPoint[] dPoints = new DataPoint[length];
    DataPoint[] jPoints = new DataPoint[length];

    kPoints[0] = new DataPoint(exchanges[0].getDate(), 100);
    kPoints[1] = new DataPoint(exchanges[1].getDate(), 100);
    dPoints[0] = new DataPoint(exchanges[0].getDate(), 100);
    dPoints[1] = new DataPoint(exchanges[1].getDate(), 100);
    jPoints[0] = new DataPoint(exchanges[0].getDate(), 100);
    jPoints[1] = new DataPoint(exchanges[1].getDate(), 100);

    for (int i = 2; i < length; i++) {
      double lastK = kPoints[i - 1].getValue();
      double lastD = dPoints[i - 1].getValue();

      kPoints[i] = new DataPoint(exchanges[i].getDate(), 2.0 / 3 * lastK + 1.0 / 3 * rsvPoints[i].getValue());
      dPoints[i] = new DataPoint(exchanges[i].getDate(), 2.0 / 3 * lastD + 1.0 / 3 * kPoints[i].getValue());
      jPoints[i] = new DataPoint(exchanges[i].getDate(), 3 * kPoints[i].getValue() - 2 * dPoints[i].getValue());
    }

    return new KDJ(new TimeSeries(kPoints), new TimeSeries(dPoints), new TimeSeries(jPoints));
  }
}
