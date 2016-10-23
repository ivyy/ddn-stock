package com.ddn.stock.service;

import java.time.LocalDate;

public interface ImportService {
  void importDailyTicksFromYahoo(String stockCode);
  void importDailyTicksFromYahoo(String stockCode, LocalDate startDate);
  void importPowerFactorFromSina(String stockCode);
  void importPowerFactorFromSina(String stockCode, int year, int quarter);
  void importFromScratch();
}
