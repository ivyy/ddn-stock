package com.ddn.stock.service;

public interface ImportService {
  void fromYahoo(String stockCode);
  void fromYahoo(String stockCode, String start, String end);
}
