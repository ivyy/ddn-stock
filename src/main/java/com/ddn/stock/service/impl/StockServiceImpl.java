package com.ddn.stock.service.impl;

import com.ddn.stock.domain.Stock;
import com.ddn.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public void save(Stock stock) {
    mongoTemplate.save(stock);
  }

  @Override
  public Stock findOne(String stockCode) {
    return mongoTemplate.findOne(query(where("code").is(stockCode)), Stock.class);
  }

  @Override
  public List<Stock> findAll() {
    return mongoTemplate.findAll(Stock.class);
  }

  @Override
  public List<Stock> findByMarket(String market) {
    return mongoTemplate.find(query(where("market").is(market)), Stock.class);
  }
}
