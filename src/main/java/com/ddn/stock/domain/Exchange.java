package com.ddn.stock.domain;


public class Exchange {
  private String id;
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

  public void setStockCode(String stockCode) {
    this.stockCode = stockCode;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setOpen(float open) {
    this.open = open;
  }

  public void setClose(float close) {
    this.close = close;
  }

  public void setHigh(float high) {
    this.high = high;
  }

  public void setLow(float low) {
    this.low = low;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
