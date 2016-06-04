package com.ddn.stock.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
  A TimeSeries contains a list of data points order by dates ascending
 */
public class TimeSeries {

  private DataPoint[] points;

  public static final TimeSeries EMPTY_SERIAL = new TimeSeries(new DataPoint[0]);

  public TimeSeries(DataPoint[] points) {
    Arrays.sort(points, (DataPoint p1, DataPoint p2) -> p1.getDate().compareTo(p2.getDate()));
    this.points = points;
  }

  public DataPoint[] getPoints() {
    return points;
  }

  public int length() {
    return this.points.length;
  }

  @Override
  public String toString() {
    return "TimeSeries{" +
        "points=" + Arrays.toString(points) +
        '}';
  }
}
