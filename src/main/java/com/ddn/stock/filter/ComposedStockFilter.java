package com.ddn.stock.filter;

import com.ddn.stock.domain.document.Stock;

import java.util.List;

public class ComposedStockFilter extends StockFilter {

  private List<StockFilter> stockFilters;

  public ComposedStockFilter(List<StockFilter> stockFilters) {
    this.stockFilters = stockFilters;
  }

  @Override
  public boolean checkAtDate(Stock stock, String date) {
    boolean result = true;
    for (StockFilter stockFilter: stockFilters) {
      result = result && stockFilter.checkAtDate(stock, date);
    }
    return result;
  }
}
