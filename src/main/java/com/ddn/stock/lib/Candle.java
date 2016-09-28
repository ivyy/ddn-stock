package com.ddn.stock.lib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candle {
  double open;
  double close;
  double high;
  double low;
  double volume;

  public double body() {
    return Math.abs(close - open);
  }

  public double upperShadow() {
    return high - Math.max(open, close);
  }

  public double lowerShadow() {
    return Math.min(open,close) - low;
  }

  public double shadow() {
    return upperShadow() + lowerShadow();
  }

  public double range() {
    return high - low;
  }
}
