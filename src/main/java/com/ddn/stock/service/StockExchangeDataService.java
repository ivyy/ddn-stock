package com.ddn.stock.service;

import com.ddn.stock.domain.Exchange;

import java.util.List;

public interface StockExchangeDataService {
  public List<Exchange> getAllHistoricalData(String stockCode);
}
