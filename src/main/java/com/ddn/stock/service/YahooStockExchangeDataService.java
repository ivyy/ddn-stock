package com.ddn.stock.service;

import com.ddn.stock.domain.Exchange;

import java.util.List;

public interface YahooStockExchangeDataService {
  public List<Exchange> getAllHistoricalData(String stockCode);
}
