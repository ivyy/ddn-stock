package com.ddn.stock.lib;

public class CandleBuilder {
  private Candle candle = new Candle();

  public CandleBuilder high(double high) {
    candle.setHigh(high);
    return this;
  }

  public CandleBuilder low(double low) {
    candle.setLow(low);
    return this;
  }

  public CandleBuilder open(double open) {
    candle.setOpen(open);
    return this;
  }

  public CandleBuilder close(double close) {
    candle.setClose(close);
    return this;
  }

  public CandleBuilder volume(double volume) {
    candle.setVolume(volume);
    return this;
  }

  public Candle build() {
    return candle;
  }
}
