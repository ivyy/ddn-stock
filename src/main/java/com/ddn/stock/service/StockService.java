package com.ddn.stock.service;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.domain.Stock;

public interface StockService {
  void save(Stock stock);
  void updateBasic(String stockCode, String description, String displayName);
  void recalculateAllIndicators(String stockCode);
  void appendDailyExchangeData(String stockCode, Exchange exchange);
}
