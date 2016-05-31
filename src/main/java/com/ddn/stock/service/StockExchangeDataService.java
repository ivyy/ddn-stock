package com.ddn.stock.service;

import com.ddn.stock.domain.StockExchangeData;

/**
 * Created by chenzi on 5/31/2016.
 */
public interface StockExchangeDataService {
  public StockExchangeData[] getAllHistoricalData(String stockCode);
}
