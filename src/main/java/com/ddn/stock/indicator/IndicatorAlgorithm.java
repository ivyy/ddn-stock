package com.ddn.stock.indicator;

import com.ddn.stock.domain.Exchange;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndicatorAlgorithm {

  public TimeSeries sma(TimeSeries timeSeries, int n) {
    if (n < 1) throw new IllegalArgumentException("n must be larger than 0");

    int length = timeSeries.length();
    TimePoint[] points = timeSeries.getPoints();
    TimePoint[] output = new TimePoint[length];

    double sum = 0;
    int i = 0, j = 0;

    for (i = 0; i < length; i++) {
      sum += points[i].getValue();
      if (i < n - 1) {
        output[j++] = new TimePoint(points[i].getDate(), Double.MAX_VALUE);
      } else if (i == n - 1) {
        output[j++] = new TimePoint(points[i].getDate(), sum / n);
      } else if (i >= n) {
        sum -= points[i - n].getValue();
        output[j++] = new TimePoint(points[i].getDate(), sum / n);
      }
    }
    return new TimeSeries(output);
  }

  public TimeSeries ema(TimeSeries timeSeries, int n) {

    if (n < 1) {
      return TimeSeries.EMPTY_SERIAL;
    }

    int length = timeSeries.length();
    TimePoint[] points = timeSeries.getPoints();

    TimePoint[] output = new TimePoint[length];

    float a = 2.0f / (n + 1);
    int i = 0;

    for (i = 0; i < length; i++) {
      if (i == 0) {
        output[i] = points[i];
      } else {
        output[i] = new TimePoint(points[i].getDate(),
            a * (points[i].getValue() - output[i - 1].getValue()) + output[i - 1].getValue());
      }
    }

    return new TimeSeries(output);
  }

  public MACD macd(TimeSeries timeSeries, MACDParam macdParam) {

    int length = timeSeries.length();

    TimePoint[] diff = new TimePoint[length];

    TimePoint[] low = ema(timeSeries, macdParam.getLow()).getPoints();
    TimePoint[] high = ema(timeSeries, macdParam.getHigh()).getPoints();

    for (int i = 0; i < length; i++) {
      diff[i] = new TimePoint(low[i].getDate(), low[i].getValue() - high[i].getValue());
    }

    TimeSeries diffTimeSeries = new TimeSeries(diff);
    TimeSeries deaTimeSeries = ema(diffTimeSeries, macdParam.getMiddle());

    return new MACD(diffTimeSeries, deaTimeSeries);
  }

  public MACD macd(TimeSeries timeSeries) {
    return macd(timeSeries, MACDParam.DEFAULT);
  }

  public KLine kLine(List<Exchange> exchangeList) {
    return new KLine(exchangeList);
  }

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
