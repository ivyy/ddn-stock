package com.ddn.stock.service;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.domain.Stock;

import java.util.List;

public interface StockService {
  void save(Stock stock);
  Stock findOne(String stockCode);
  List<Stock> findAll();
  List<Stock> findByMarket(String market);
}
