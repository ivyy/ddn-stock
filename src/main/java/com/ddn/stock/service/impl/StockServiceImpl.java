package com.ddn.stock.service.impl;

import com.ddn.stock.domain.document.Stock;
import com.ddn.stock.domain.document.Tick;
import com.ddn.stock.service.StockService;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private Datastore datastore;

  @Override
  public void saveOrUpdate(Stock stock) {
    datastore.save(stock);
  }

  @Override
  public Stock findOne(String stockCode) {
    return datastore.createQuery(Stock.class).field("code").equal(stockCode).get();
  }

  @Override
  public List<Stock> findAll() {
    return datastore.find(Stock.class).asList();
  }

  @Override
  public void delete(String stockCode) {
    datastore.findAndDelete(datastore.createQuery(Stock.class).field("code").equal(stockCode));
  }

  @Override
  public void deleteAll() {
    datastore.delete(datastore.createQuery(Stock.class));
  }

  @Override
  public void fillInitialDate(String stockCode) {
    //first try to get the stock object
    Stock stock = findOne(stockCode);
    if (stock != null && stock.getInitialDate() == null) {
      //HELP: I don't know how to get the minial date in mongodb with morphia
      //Query query = datastore.find(Tick.class).order("-date").field("stockCode").equal(stockCode).limit(1);
      List<Tick> ticks = datastore.createQuery(Tick.class).field("stockCode").equal(stockCode).asList();
      Collections.sort(ticks, (tick1, tick2) -> tick1.getDate().compareTo(tick2.getDate()));
      Date initialDate = ticks.get(0).getDate();
      stock.setInitialDate(initialDate);
      saveOrUpdate(stock);
    }
  }
}
