package com.ddn.stock.service;

import com.ddn.stock.domain.mongo.Stock;

import java.util.List;

public interface StockService {
  void saveOrUpdate(Stock stock);
  Stock findOne(String stockCode);
  List<Stock> findAll();
  void delete(String stockCode);
  void deleteAll();
}
