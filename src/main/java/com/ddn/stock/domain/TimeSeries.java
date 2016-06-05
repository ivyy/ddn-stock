package com.ddn.stock.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/*
  A TimeSeries contains a list of data points order by dates ascending
 */
public class TimeSeries {

  private DataPoint[] points;

  private Map<String, DataPoint> map = new HashMap<>();

  private Map<String, Integer> indexes = new HashMap<>();

  public static final TimeSeries EMPTY_SERIAL = new TimeSeries(new DataPoint[0]);

  public TimeSeries(DataPoint[] points) {
    Arrays.sort(points, (DataPoint p1, DataPoint p2) -> p1.getDate().compareTo(p2.getDate()));
    this.points = points;
    Stream.of(this.points).forEach(p -> map.put(p.getDate(), p));
    for (int i = 0; i < this.points.length; i++) {
      indexes.put(this.points[i].getDate(), i);
    }
  }

  public TimeSeries betweenDate(String fromDate, String toDate) {
    DataPoint[] dataPoints = Stream.of(this.points)
        .filter((DataPoint p) ->
            p.getDate().compareTo(fromDate) >= 0 && p.getDate().compareTo(toDate) <= 0)
        .toArray(size -> new DataPoint[size]);

    return new TimeSeries(dataPoints);
  }

  public DataPoint[] getPoints() {
    return points;
  }

  public int length() {
    return this.points.length;
  }

  public double valueAt(String date) {
    if (this.map.containsKey(date)) {
      return map.get(date).getValue();
    }

    return 0.00f;
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


}
