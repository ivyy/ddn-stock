package com.ddn.stock.strategy.impl;

import com.ddn.stock.domain.DataPoint;
import com.ddn.stock.domain.Exchange;
import com.ddn.stock.domain.MACD;
import com.ddn.stock.domain.TimeSeries;
import com.ddn.stock.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.DoubleAccumulator;

public class SimpleStrategy implements Strategy {

  private String stockCode;

  private Exchange[] exchanges;

  private double principal = 10000;

  private long lot = 0;

  public SimpleStrategy(String stockCode, Exchange[] exchanges) {
    this.stockCode = stockCode;
    Arrays.sort(exchanges, (s,t) -> s.getDate().compareTo(t.getDate()));
    this.exchanges = exchanges;
  }

  @Override
  public void apply() {
    TimeSeries closePriceTimeSeries = new TimeSeries(
        Arrays.stream(exchanges)
            .map(exchange -> new DataPoint(exchange.getDate(), exchange.getClose()))
            .sorted((s,t) -> s.getDate().compareTo(t.getDate()))
            .toArray(size -> new DataPoint[size]));

    TimeSeries ma10TimeSeries = closePriceTimeSeries.sma(10);
    MACD macdTimeSeries = closePriceTimeSeries.macd();

    //assume every buy and sell with full principal
    //and always can buy and sell successfully
    for (int i = 1; i < exchanges.length; i++) {
      double close = exchanges[i].getClose();
      double open = exchanges[i].getOpen();
      String date = exchanges[i].getDate();
      double ma10 = ma10TimeSeries.valueAt(date);
      double lastMa10 = ma10;
      if (ma10 != Double.MIN_VALUE) {
        ma10TimeSeries.getPoints()[ma10TimeSeries.indexOfDate(date) - 1].getValue();
      }
      double macd = macdTimeSeries.getMacd().valueAt(date);

      if (close >= open && close > ma10 && macd > 0 && (ma10 != Double.MIN_VALUE && ma10 >= lastMa10)) {
        buy(date, close);
      }

      if (ma10 != Double.MIN_VALUE && ma10 < lastMa10 && close <= open && close < ma10 && (ma10 - close) / ma10 > 0.08) {
        sell(date, close);
      }

      //sell anyway at last day
      if (lot > 0 && i == exchanges.length - 1) {
        sell(date, close);
      }
    }


    System.out.println("[FINAL]" + "principal: " + this.principal + ", earning: " + (this.principal - 10000) / 10000 * 100 + "%");
  }

  private void buy(String date, double price) {
    lot = Math.round(this.principal / (price * 100));
    this.principal = this.principal - lot * price * 100;
    System.out.println("[" + date + "] buy " + lot + " lot at price: " + price + ", remaining principal: " + this.principal);
  }

  private void sell(String date, double price) {
    this.principal = this.principal + price * this.lot;
    System.out.println("[" + date + "] sell " + lot + " lot at price: " + price + ", remaining principal: " + this.principal);
    this.lot = 0;
  }

}
