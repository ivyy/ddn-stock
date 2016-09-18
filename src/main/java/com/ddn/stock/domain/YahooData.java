package com.ddn.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YahooData {
  private String id;
  private String stockCode;
  private String date;
  private double open;
  private double close;
  private double high;
  private double low;
  private double volume;
}
