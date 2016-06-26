package com.ddn.stock.indicator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/*
  A TimeSeries contains a list of data points order by dates ascending
 */
public class TimeSeries {

  private TimePoint[] points;

  private Map<String, TimePoint> map = new HashMap<>();

  private Map<String, Integer> indexes = new HashMap<>();

  public static final TimeSeries EMPTY_SERIAL = new TimeSeries(new TimePoint[0]);

  public TimeSeries(TimePoint[] points) {
    Arrays.sort(points, (TimePoint p1, TimePoint p2) -> p1.getDate().compareTo(p2.getDate()));
    this.points = points;
    //A TimeSeries must with date ascending
    Stream.of(this.points).forEach(p -> map.put(p.getDate(), p));
    for (int i = 0; i < this.points.length; i++) {
      indexes.put(this.points[i].getDate(), i);
    }
  }

  public TimeSeries betweenDate(String fromDate, String toDate) {
    TimePoint[] timePoints = Stream.of(this.points)
        .filter((TimePoint p) ->
            p.getDate().compareTo(fromDate) >= 0 && p.getDate().compareTo(toDate) <= 0)
        .toArray(size -> new TimePoint[size]);

    return new TimeSeries(timePoints);
  }

  public TimePoint[] getPoints() {
    return points;
  }

  public int length() {
    return this.points.length;
  }

  public double valueAt(String date) {
    if (this.map.containsKey(date)) {
      return map.get(date).getValue();
    }
    return Double.MAX_VALUE;
  }

  public int indexOfDate(String date) {
    if (indexes.containsKey(date)) {
      return indexes.get(date);
    }

    return -1;
  }

  @Override
  public String toString() {
    return "TimeSeries{" +
        "points=" + Arrays.toString(points) +
        '}';
  }

  public TimeSeries sma(int n) {
    if (n < 1) throw new IllegalArgumentException("n must be larger than 0");

    int length = length();

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

  public TimeSeries ema(int n) {

    if (n < 1) {
      return TimeSeries.EMPTY_SERIAL;
    }

    int length = length();

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

  public MACD macd(MACDParam macdParam) {

    TimePoint[] diff = new TimePoint[length()];

    TimePoint[] low = ema(macdParam.getLow()).getPoints();
    TimePoint[] high = ema(macdParam.getHigh()).getPoints();

    for (int i = 0; i < length(); i++) {
      diff[i] = new TimePoint(low[i].getDate(), low[i].getValue() - high[i].getValue());
    }

    TimeSeries diffTimeSeries = new TimeSeries(diff);
    TimeSeries deaTimeSeries = diffTimeSeries.ema(macdParam.getMiddle());

    return new MACD(diffTimeSeries, deaTimeSeries);
  }

  public MACD macd() {
    return macd(MACDParam.DEFAULT);
  }

  public boolean crossoverWith(TimeSeries other, String atDate) {

    double thisCurrentValue = this.valueAt(atDate);
    double otherCurrentValue = other.valueAt(atDate);

    int i = this.indexOfDate(atDate);
    if (i > 0) {
      double thisLastValue = this.points[i - 1].getValue();
      double otherLastValue = other.getPoints()[i - 1].getValue();
      return thisLastValue <= otherLastValue && thisCurrentValue >= otherCurrentValue;
    }

    return false;
  }

  public boolean deadCrossingWith(TimeSeries other, String atDate) {

    double thisCurrentValue = this.valueAt(atDate);
    double otherCurrentValue = other.valueAt(atDate);

    int i = this.indexOfDate(atDate);
    if (i > 0) {
      double thisLastValue = this.points[i - 1].getValue();
      double otherLastValue = other.getPoints()[i - 1].getValue();
      return thisLastValue >= otherLastValue && thisCurrentValue <= otherCurrentValue;
    }

    return false;
  }
}
