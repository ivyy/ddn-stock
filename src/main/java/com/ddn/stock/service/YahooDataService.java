package com.ddn.stock.service;

import com.ddn.stock.domain.document.Tick;

import java.time.LocalDate;
import java.util.List;

public interface YahooDataService {

  List<Tick> getAll(String stockCode);

  List<Tick> getFrom(String stockCode, LocalDate startDate);

}
