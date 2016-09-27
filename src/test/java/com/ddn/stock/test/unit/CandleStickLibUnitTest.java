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




}
