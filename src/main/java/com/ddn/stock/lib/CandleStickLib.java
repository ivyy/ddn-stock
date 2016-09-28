package com.ddn.stock.lib;

import org.springframework.stereotype.Component;

@Component
public class CandleStickLib {

  private double ERROR = 0.001;
  private double PERCENT = 0.01;

  public boolean isDaYangXian(Candle candle) {
    /*
      - 开盘价大于收盘价
      - 实体部分相对于开盘价上涨超过4%
      - 上影线很短
      - 下影线很短
      - 实体部分占75%以上
     */
    return (candle.close > candle.open)
        && candle.body() / candle.open > 4 * PERCENT
        && candle.upperShadow() / candle.range() < 0.2
        && candle.lowerShadow() / candle.range() < 0.2
        && candle.body() / candle.range() > 75 * PERCENT
        ;
  }

  public boolean isYangXian(Candle candle) {
    return candle.close - candle.open > ERROR;
  }

  public boolean isYingXian(Candle candle) {
    return candle.close - candle.open < -1 * ERROR;
  }

  public boolean isDaYingXian(Candle candle) {

    return (candle.close < candle.open)
        && candle.body() / candle.open > 4 * PERCENT
        && candle.upperShadow() / candle.range() < 0.2
        && candle.lowerShadow() / candle.range() < 0.2
        && candle.body() / candle.range() > 75 * PERCENT
        ;
  }

  public boolean isShiZiXing(Candle candle) {
    //上下影线都比实体部分长,并且实体的比例小于当前中间价=(开盘+收盘)/2的1%
    return candle.upperShadow() / candle.body() > 1
        && candle.lowerShadow() / candle.body() > 1
        && candle.body() / ((candle.open + candle.close) / 2) < 0.01;
  }

}
