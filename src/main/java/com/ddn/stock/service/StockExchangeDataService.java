package com.ddn.stock.service;

import com.ddn.stock.domain.Exchange;

/**
 * Created by chenzi on 5/31/2016.
 */
public interface StockExchangeDataService {
  public Exchange[] getAllHistoricalData(String stockCode);
}
