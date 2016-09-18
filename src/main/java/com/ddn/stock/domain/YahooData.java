package com.ddn.stock.domain;


public class YahooData {
  private String id;
  private String stockCode;
  private String date;
  private double open;
  private double close;
  private double high;
  private double low;
  private double volume;

  public YahooData(String stockCode, String date, double open, double close, double high, double low, double volume) {
    this.stockCode = stockCode;
    this.date = date;
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
    this.volume = volume;
  }

  public String getStockCode() {
    return stockCode;
  }

  public String getDate() {
    return date;
  }

  public double getOpen() {
    return open;
  }

  public double getClose() {
    return close;
  }

  public double getHigh() {
    return high;
  }

  public double getLow() {
    return low;
  }

  public double getVolume() {
    return volume;
  }

  public void setStockCode(String stockCode) {
    this.stockCode = stockCode;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
