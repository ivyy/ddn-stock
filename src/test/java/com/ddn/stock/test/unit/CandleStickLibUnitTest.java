package com.ddn.stock.test.unit;

import com.ddn.stock.lib.Candle;
import com.ddn.stock.lib.CandleBuilder;
import com.ddn.stock.lib.CandleStickLib;
import org.junit.Test;

import static org.junit.Assert.*;

public class CandleStickLibUnitTest {

  private CandleStickLib csLib = new CandleStickLib();

  @Test
  public void test_isYangXian() {
    //600446 2016-08-12 YES
    Candle candle = new CandleBuilder().open(30.60).close(31.06).high(31.15).low(30.17).build();
    assertTrue(csLib.isYangXian(candle));
  }

  @Test
  public void test_isDaYangXian() {
    //600446 2016-05-20  YES
    Candle candle = new CandleBuilder().open(27.45).close(29.50).high(29.70).low(27.09).build();
    assertTrue(csLib.isDaYangXian(candle));

    //600446 2016-08-08  NO
    Candle candle1 = new CandleBuilder().open(30.83).close(31.60).high(31.71).low(30.40).build();
    assertFalse(csLib.isDaYangXian(candle1));
  }

  @Test
  public void test_isYingXian() {
    //600446 2016-03-03 YES
    Candle candle = new CandleBuilder().open(27.29).close(26.76).high(28.12).low(26.76).build();
    assertTrue(csLib.isYingXian(candle));

    //600446 2016-03-01 YES
    Candle candle1 = new CandleBuilder().open(24.70).close(24.66).high(25.33).low(22.55).build();
    assertTrue(csLib.isYingXian(candle1));
  }

  @Test
  public void test_isShiZiXing() {
    //600446 2016-05-10 YES
    Candle candle = new CandleBuilder().open(27.60).close(27.74).high(27.92).low(27.39).build();
    assertTrue(csLib.isShiZiXing(candle));
    //600446 2016-06-16
    Candle candle1 = new CandleBuilder().open(33.10).close(33.07).high(33.77).low(32.72).build();
    assertTrue(csLib.isShiZiXing(candle1));
    //600446 2016-06-24
    Candle candle2 = new CandleBuilder().open(35.66).close(35.83).high(36.70).low(33.80).build();
    assertTrue(csLib.isShiZiXing(candle2));

    //600446 2016-06-01
    Candle candle3 = new CandleBuilder().high(32.50).open(31.38).low(31.12).close(31.70).build();
    assertFalse(csLib.isShiZiXing(candle3));

    //600446 2016-04-15
    Candle candle4 = new CandleBuilder().high(36.72).open(36.42).low(35.72).close(36.05).build();
    assertFalse(csLib.isShiZiXing(candle4));

    //600519 2016-05-09
    Candle candle5 = new CandleBuilder().high(247.47).open(244.85).low(243.76).close(245.53).build();
    assertTrue(csLib.isShiZiXing(candle5));

    //600519 2016-03-29
    Candle candle6 = new CandleBuilder().high(245.70).open(244.33).low(239.28).close(242.88).build();
    assertFalse(csLib.isShiZiXing(candle6));
  }


}
