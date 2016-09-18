package com.ddn.stock.service;

import com.ddn.stock.domain.YahooData;

import java.util.List;

public interface YahooDataService {
  public List<YahooData> getAllHistoricalData(String stockCode);
  public List<YahooData> getHistoryBetween(String stockCode, String start, String end);
}