package com.ddn.stock.junit;

import com.ddn.stock.domain.DataPoint;
import com.ddn.stock.domain.TimeSeries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JunitConfig.class)
public class IndicatorAlgorithmUnitTest {

  @Test
  public void testSma() {
    //from 600549
    TimeSeries timeSeries = new TimeSeries(new DataPoint[]{
        new DataPoint("2016-05-19", 23.20f),
        new DataPoint("2016-05-20", 22.86f),
        new DataPoint("2016-05-23", 22.64f),
        new DataPoint("2016-05-24", 22.69f),
        new DataPoint("2016-05-25", 22.51f),
        new DataPoint("2016-05-26", 23.59f),
        new DataPoint("2016-05-27", 24.19f),
        new DataPoint("2016-05-30", 24.37f),
        new DataPoint("2016-05-31", 26.81f),
        new DataPoint("2016-06-01", 29.26f),
        new DataPoint("2016-06-02", 29.15f),
        new DataPoint("2016-06-03", 29.78f)
    });

    TimeSeries sma5 = timeSeries.sma(5);
    assertEquals(27.87f, sma5.valueAt("2016-06-03"), 0.01);
    TimeSeries sma10 = timeSeries.sma(10);
    assertEquals(25.50f, sma10.valueAt("2016-06-03"), 0.01);
  }


}
