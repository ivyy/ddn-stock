package com.ddn.stock.indicator;

public class TimePoint {
  private String date;
  private double value;

  public TimePoint(String date, double value) {
    this.date = date;
    this.value = value;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "(" + date + "," + value + ")";
  }
}
