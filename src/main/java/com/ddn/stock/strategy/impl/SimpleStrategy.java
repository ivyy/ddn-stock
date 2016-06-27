package com.ddn.stock.strategy.impl;

import com.ddn.stock.indicator.TimePoint;
import com.ddn.stock.domain.Exchange;
import com.ddn.stock.indicator.MACD;
import com.ddn.stock.indicator.TimeSeries;
import com.ddn.stock.strategy.Strategy;

import java.util.Arrays;

public class SimpleStrategy implements Strategy {

  private String stockCode;

  private Exchange[] exchanges;

  private double principal = 10000;
  private double oldPrincipal = 10000;

  private long lot = 0;

  private double buyPrice = 0;

  public SimpleStrategy(String stockCode, Exchange[] exchanges) {
    this.stockCode = stockCode;
    Arrays.sort(exchanges, (s, t) -> s.getDate().compareTo(t.getDate()));
    this.exchanges = exchanges;
  }

  @Override
  public void apply(String stockCode) {
    TimeSeries closePriceTimeSeries = new TimeSeries(
        Arrays.stream(exchanges)
            .map(exchange -> new TimePoint(exchange.getDate(), exchange.getClose()))
            .sorted((s, t) -> s.getDate().compareTo(t.getDate()))
            .toArray(size -> new TimePoint[size]));

    TimeSeries ma10TimeSeries = closePriceTimeSeries.sma(10);
    TimeSeries ma20TimeSeries = closePriceTimeSeries.sma(20);
    MACD macdTimeSeries = closePriceTimeSeries.macd();

    //assume every buy and sell with full principal
    //and always can buy and sell successfully
    for (int i = 1; i < exchanges.length; i++) {
      double close = exchanges[i].getClose();
      double open = exchanges[i].getOpen();
      String date = exchanges[i].getDate();
      double ma10 = ma10TimeSeries.valueAt(date);
      double ma20 = ma20TimeSeries.valueAt(date);
      double lastMa10 = ma10TimeSeries.getPoints()[ma10TimeSeries.indexOf(date) - 1].getValue();
      double macd = macdTimeSeries.getMacd().valueAt(date);
      double diff = macdTimeSeries.getDiff().valueAt(date);

      if (close > open && close > ma10
          && ma10 > lastMa10 && ma10 > ma20
          && macd > 0) {
        buy(date, close);
      }

      if ((ma10 <= lastMa10 && close < open && close < ma10 && (ma10 - close) / ma10 > 0.03) || (buyPrice - close) / buyPrice > 0.05) {
        if (lot > 0) {
          sell(date, close);
        }
      }

      //sell anyway at last day
      if (lot > 0 && i == exchanges.length - 1) {
        sell(date, close);
      }
    }

    System.out.println("[FINAL]" + "principal: " + this.principal + ", earning: " + (this.principal - 10000) / 10000 * 100 + "%");
  }

  private void buy(String date, double price) {
    long toBuy = (new Double(this.principal / (price * 100))).longValue();
    if (toBuy > 0) {
      this.principal = this.principal - toBuy * price * 100;
      this.lot += toBuy;
      this.buyPrice = price;
      System.out.println(String.format("[%s] buy %d lot at price: %.2f, remaining: %.2f, total lot: %d", date, toBuy, price, this.principal, this.lot));
      //System.out.println("[" + date + "] buy " + toBuy + " lot at price: " + price + ", remaining principal: " + this.principal + " total lot: " + this.lot);
    }
  }

  private void sell(String date, double price) {
    this.principal = this.principal + price * this.lot * 100;
//    System.out.println("[" + date + "] sell " + lot + " lot at price: " + price + ", remaining principal: "
//        + this.principal + " earning " + (this.principal - this.oldPrincipal)/ this.oldPrincipal * 100 + "%");
    System.out.println(String.format("[%s] sell %d lot at price: %.2f, remaining: %.2f, earning %.2f%%", date, lot, price, principal,
        (this.principal - this.oldPrincipal) / this.oldPrincipal * 100));
    this.lot = 0;
    this.oldPrincipal = this.principal;
  }

}
