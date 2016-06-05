package com.ddn.stock.domain;

public class MACDParam {
  private int low;
  private int high;
  private int middle;

  public MACDParam(int low, int middle, int high) {
    this.low = low;
    this.middle = middle;
    this.high = high;
  }

  public static final MACDParam DEFAULT = new MACDParam(12,9,26);

  public int getLow() {
    return low;
  }

  public int getHigh() {
    return high;
  }

  public int getMiddle() {
    return middle;
  }
}
