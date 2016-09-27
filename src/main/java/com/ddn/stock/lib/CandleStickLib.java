package com.ddn.stock.lib;

import org.springframework.stereotype.Component;

@Component
public class CandleStickLib {

  private double ERROR = 0.001;
  private double PERCENT = 0.01;

  public boolean isDaYangXian(Candle candle) {
    //上下影线的长度
    double range = candle.high - candle.low;
    if (range == 0) return false;

    return (candle.close > candle.open)
        && (candle.close - candle.open) / candle.open > 4 * PERCENT
        && (candle.high - candle.close) / range < 0.2
        && (candle.open - candle.low) / range < 0.2;
  }

  public boolean isYangXian(Candle candle) {
    return candle.close - candle.open > ERROR;
  }

  public boolean isYingXian(Candle candle) {
    return candle.close - candle.open < -1 * ERROR;
  }

  public boolean isDaYingXian(Candle candle) {
    double range = candle.high - candle.low;
    if (range == 0) return false;

    return (candle.close < candle.open)
        && (candle.close - candle.open) / candle.open < -4 * PERCENT
        && (candle.high - candle.open) / range < 0.2
        && (candle.close - candle.low) / range < 0.2;
  }

  public boolean isShiZiXing(Candle candle) {
    return candle.upperShadow() > 0 && candle.lowerShadow() > 0 && candle.body() / candle.upperShadow() < 1;
  }

}
