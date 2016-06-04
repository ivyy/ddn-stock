package com.ddn.stock.domain;

public class DataPoint {
  private String date;
  private float value;

  public DataPoint(String date, float value) {
    this.date = date;
    this.value = value;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "(" + date + "," + value + ")";
  }
}
