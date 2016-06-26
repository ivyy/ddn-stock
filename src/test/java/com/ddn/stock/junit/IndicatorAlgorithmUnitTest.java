package com.ddn.stock.junit;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.indicator.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JunitConfig.class)
public class IndicatorAlgorithmUnitTest {

  @Autowired
  private IndicatorAlgorithm indicatorAlgorithm;

  @Test
  public void testSma() {
    //from 600549
    TimeSeries timeSeries = new TimeSeries(new TimePoint[]{
        new TimePoint("2016-05-19", 23.20f),
        new TimePoint("2016-05-20", 22.86f),
        new TimePoint("2016-05-23", 22.64f),
        new TimePoint("2016-05-24", 22.69f),
        new TimePoint("2016-05-25", 22.51f),
        new TimePoint("2016-05-26", 23.59f),
        new TimePoint("2016-05-27", 24.19f),
        new TimePoint("2016-05-30", 24.37f),
        new TimePoint("2016-05-31", 26.81f),
        new TimePoint("2016-06-01", 29.26f),
        new TimePoint("2016-06-02", 29.15f),
        new TimePoint("2016-06-03", 29.78f)
    });

    TimeSeries sma5 = timeSeries.sma(5);
    assertEquals(27.87f, sma5.valueAt("2016-06-03"), 0.01);
    TimeSeries sma10 = timeSeries.sma(10);
    assertEquals(25.50f, sma10.valueAt("2016-06-03"), 0.01);
  }

  @Test
  public void testKDJ() {
    //from 601211
    KLine kLine = new KLine(new Exchange[]{
        new Exchange("601211.ss", "2015-06-26", 23.65, 28.38, 28.38, 23.65, 2068000),
        new Exchange("601211.ss", "2015-06-29", 31.22, 31.22, 31.22, 29.18, 314822900),
        new Exchange("601211.ss", "2015-06-30", 30.68, 34.34, 34.34, 29.18, 521100300),
        new Exchange("601211.ss", "2015-07-01", 36.00, 34.82, 37.30, 34.03, 529359000),
        new Exchange("601211.ss", "2015-07-02", 35.80, 31.42, 35.88, 31.35, 355393400),
        new Exchange("601211.ss", "2015-07-03", 30.01, 29.60, 33.30, 28.31, 331084300),
        new Exchange("601211.ss", "2015-07-06", 32.56, 31.95, 32.56, 29.80, 429413000),
        new Exchange("601211.ss", "2015-07-07", 30.00, 28.76, 30.90, 28.76, 281586300),
        new Exchange("601211.ss", "2015-07-08", 25.88, 25.88, 25.88, 25.88, 42357000),
        new Exchange("601211.ss", "2015-07-09", 23.29, 28.47, 28.47, 23.29, 388972300),
        new Exchange("601211.ss", "2015-07-10", 28.50, 31.32, 31.32, 28.47, 367948200),
        new Exchange("601211.ss", "2015-07-13", 31.50, 32.82, 33.98, 30.38, 448924400)
    });

    KDJ kdj = indicatorAlgorithm.kdj(kLine);

    assertEquals(100, kdj.getkTimeSeries().valueAt("2015-06-26"), 0.001);
    assertEquals(100, kdj.getdTimeSeries().valueAt("2015-06-26"), 0.001);
    assertEquals(100, kdj.getjTimeSeries().valueAt("2015-06-26"), 0.001);

    assertEquals(41.123, kdj.getkTimeSeries().valueAt("2015-07-09"), 0.001);
    assertEquals(54.960, kdj.getdTimeSeries().valueAt("2015-07-09"), 0.001);
    assertEquals(13.449, kdj.getjTimeSeries().valueAt("2015-07-09"), 0.001);

  }

}
