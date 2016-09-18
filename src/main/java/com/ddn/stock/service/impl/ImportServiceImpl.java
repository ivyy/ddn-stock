package com.ddn.stock.service.impl;

import com.ddn.stock.domain.YahooData;
import com.ddn.stock.domain.mongo.History;
import com.ddn.stock.service.ImportService;
import com.ddn.stock.service.YahooDataService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * this service load data from yahoo and save it into mongodb
 */
@Service
public class ImportServiceImpl implements ImportService {

  @Autowired
  private YahooDataService yahooDataService;

  @Autowired
  private Datastore datastore;

  private History yahooDataToHistory(YahooData data) {
    History history = new History();
    history.setClose(data.getClose());
    history.setCode(data.getStockCode());
    history.setDate(data.getDate());
    history.setHigh(data.getHigh());
    history.setLow(data.getLow());
    history.setOpen(data.getOpen());
    history.setVolume(data.getVolume());
    return history;
  }

  /*
    load all from scratch
   */
  @Override
  public void fromYahoo(String stockCode) {
    List<History> histories = yahooDataService.getAllHistoricalData(stockCode)
        .stream().map(yahooData -> yahooDataToHistory(yahooData)).collect(Collectors.toList());
    datastore.delete(datastore.createQuery(History.class).field("code").equal(stockCode));
    datastore.save(histories);
  }

  /*
    load data between two date
   */
  @Override
  public void fromYahoo(String stockCode, String start, String end) {
    List<History> histories = yahooDataService.getHistoryBetween(stockCode, start, end)
        .stream().map(yahooData -> yahooDataToHistory(yahooData)).collect(Collectors.toList());
    Query query = datastore.createQuery(History.class)
        .field("code").equal(stockCode)
        .field("date").greaterThanOrEq(start)
        .field("date").lessThanOrEq(end);
    datastore.delete(query);
    datastore.save(histories);
  }
}
