package com.ddn.stock.domain;

/**
 * Created by chenzi on 5/31/2016.
 */
public class Exchange {
  private String stockCode;
  private String date;
  private float open;
  private float close;
  private float high;
  private float low;
  private float volume;

  public Exchange(String stockCode, String date, float open, float close, float high, float low, float volume) {
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

  public float getOpen() {
    return open;
  }

  public float getClose() {
    return close;
  }

  public float getHigh() {
    return high;
  }

  public float getLow() {
    return low;
  }

  public float getVolume() {
    return volume;
  }

}
