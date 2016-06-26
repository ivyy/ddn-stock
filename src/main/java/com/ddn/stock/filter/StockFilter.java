package com.ddn.stock.filter;

import com.ddn.stock.domain.Stock;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A StockFilter filters the input stocks and then return the matched
 */
public abstract class StockFilter {

  public final List<Stock> filterSotckList(List<Stock> stockList) {
    return stockList.parallelStream().filter(stock -> check(stock)).collect(Collectors.toList());
  }

  abstract boolean check(Stock stock);
}
