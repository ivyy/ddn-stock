package com.ddn.stock.filter;

import com.ddn.stock.domain.Stock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class ChainedStockFilter extends StockFilter {

  private List<StockFilter> stockFilters;

  public ChainedStockFilter(List<StockFilter> stockFilters) {
    this.stockFilters = stockFilters;
  }


}
