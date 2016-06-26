package com.ddn.stock.service.impl;

import com.ddn.stock.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Service
public class IndicatorServiceImpl {

  @Autowired
  private MongoTemplate mongoTemplate;

  public void recalculateAllIndicators(String stockCode) {
    //first calculate its close price time series
//    Stock stock = mongoTemplate.findOne(query(where("code").is(stockCode)), Stock.class);
//    KLine kLine = new KLine(stock.getExchangeList());
//
//    TimeSeries closePriceTimeSeries = kLine.closePriceTimeSeries();
//    TimeSeries ma5 = closePriceTimeSeries.sma(5);
//    TimeSeries ma10 = closePriceTimeSeries.sma(10);
//    TimeSeries ma20 = closePriceTimeSeries.sma(20);
//    TimeSeries ma60 = closePriceTimeSeries.sma(60);
//
//
//    Map<String, Object> indicators = new HashMap<>();
//
//    //simple average
//    indicators.put("ma5", ma5);
//    indicators.put("ma10", ma10);
//    indicators.put("ma20", ma20);
//    indicators.put("ma60", ma60);
//
//    //MACD
//    MACD macd = closePriceTimeSeries.macd();
//    HashMap<String, Object> macdObj = new HashMap<>();
//    macdObj.put("diff", macd.getDiff().getPoints());
//    macdObj.put("dea", macd.getDea().getPoints());
//    macdObj.put("macd", macd.getMacd().getPoints());
//    indicators.put("macd", macdObj);
//    //KDJ
//    KDJ kdj = indicatorAlgorithm.kdj(kLine);
//    HashMap<String, Object> kdjObj = new HashMap<>();
//    kdjObj.put("k", kdj.getkTimeSeries().getPoints());
//    kdjObj.put("d", kdj.getdTimeSeries().getPoints());
//    kdjObj.put("j", kdj.getjTimeSeries().getPoints());
//    indicators.put("kdj", kdjObj);
//
//    mongoTemplate.updateFirst(query(where("code").is(stockCode)), update("indicators", indicators), Stock.class);
  }
}
