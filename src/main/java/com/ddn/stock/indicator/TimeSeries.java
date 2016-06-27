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

  public TimeSeries slice(String fromDate, String toDate) {
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

  public int indexOf(String date) {
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

  public boolean crossoverWith(TimeSeries other, String atDate) {

    double thisCurrentValue = this.valueAt(atDate);
    double otherCurrentValue = other.valueAt(atDate);

    int i = this.indexOf(atDate);
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

    int i = this.indexOf(atDate);
    if (i > 0) {
      double thisLastValue = this.points[i - 1].getValue();
      double otherLastValue = other.getPoints()[i - 1].getValue();
      return thisLastValue >= otherLastValue && thisCurrentValue <= otherCurrentValue;
    }

    return false;
  }
}
