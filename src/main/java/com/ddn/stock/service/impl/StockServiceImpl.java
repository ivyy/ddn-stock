package com.ddn.stock.service.impl;

import com.ddn.stock.domain.mongo.Stock;
import com.ddn.stock.service.StockService;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
