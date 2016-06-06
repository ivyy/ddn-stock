package com.ddn.stock.integration;

import com.ddn.stock.Application;
import com.ddn.stock.domain.*;
import com.ddn.stock.service.YahooStockExchangeDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class IndicatorAlgorithmIntegrationTest {


  @Autowired
  private YahooStockExchangeDataService yahooStockExchangeDataService;

  @Test
  public void testMACDValue() {
    //fetch from Yahoo
    List<Exchange> exchangeList = yahooStockExchangeDataService.getAllHistoricalData("601211.ss");

    DataPoint[] dataPoints = exchangeList.stream()
        .map(exchange -> new DataPoint(exchange.getDate(), exchange.getClose()))
        .toArray(size -> new DataPoint[size]);

    TimeSeries closePriceTimeSeries = new TimeSeries(dataPoints);
    MACD macd = closePriceTimeSeries.macd(MACDParam.DEFAULT);

    assertEquals(0.227, macd.getDiff().valueAt("2015-06-29"), 0.001);
    assertEquals(0.165, macd.getDiff().valueAt("2015-07-09"), 0.001);
    assertEquals(-0.691, macd.getDiff().valueAt("2015-08-18"), 0.001);
    assertEquals(-1.654, macd.getDiff().valueAt("2015-09-21"), 0.001);
    assertEquals(-0.055, macd.getDiff().valueAt("2016-06-03"), 0.001);
    assertEquals(0.331, macd.getDea().valueAt("2016-04-11"), 0.001);
    assertEquals(0, macd.getMacd().valueAt("2016-04-13"), 0.001);
  }

  @Test
  public void testCrossover() {
    //fetch from Yahoo
    List<Exchange> exchangeList = yahooStockExchangeDataService.getAllHistoricalData("601211.ss");
    DataPoint[] dataPoints = exchangeList.stream()
        .map(exchange -> new DataPoint(exchange.getDate(), exchange.getClose()))
        .toArray(size -> new DataPoint[size]);

    TimeSeries closePriceTimeSeries = new TimeSeries(dataPoints);

    TimeSeries ma5 = closePriceTimeSeries.sma(5);
    TimeSeries ma10 = closePriceTimeSeries.sma(10);
    //test MA crossing
    assertTrue(ma5.crossoverWith(ma10, "2015-10-12"));
    assertFalse(ma5.crossoverWith(ma10, "2015-10-11"));
    assertTrue(ma5.deadCrossingWith(ma10, "2015-08-18"));

    //assert MACD crossing
    MACD macd = closePriceTimeSeries.macd(MACDParam.DEFAULT);
    assertTrue(macd.crossoverAt("2016-02-04"));
    assertTrue(macd.deadCrossingAt("2016-04-18"));
  }

}
