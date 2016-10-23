package com.ddn.stock.filter;

import com.ddn.stock.domain.document.Stock;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A StockFilter filters the input stocks and then return the matched
 */
public abstract class StockFilter {

  public final List<Stock> filterSotckList(List<Stock> stockList) {
    return stockList.parallelStream().filter(stock -> check(stock)).collect(Collectors.toList());
  }

  public final boolean check(Stock stock) {
    Date date = new Date();
    return checkAtDate(stock, String.format("%d-%d-%d", date.getYear(), date.getMonth(), date.getDate()));
  }

  abstract public boolean checkAtDate(Stock stock, String date);
}
