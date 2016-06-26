package com.ddn.stock.strategy.impl;

import com.ddn.stock.service.PositionManager;
import com.ddn.stock.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DiffLineStrategy implements Strategy {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private PositionManager positionManager;

  @Override
  public void apply(String stockCode) {

  }
}
