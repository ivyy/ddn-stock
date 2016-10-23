package com.ddn.stock.service;

import com.ddn.stock.domain.document.Stock;

import java.time.LocalDate;
import java.util.List;

public interface StockService {
  void saveOrUpdate(Stock stock);
  Stock findOne(String stockCode);
  List<Stock> findAll();
  void delete(String stockCode);
  void deleteAll();
  void fillInitialDate(String stockCode);
}
