package com.ddn.stock.test.unit;

import com.ddn.stock.lib.Candle;
import com.ddn.stock.lib.CandleBuilder;
import com.ddn.stock.lib.CandleStickLib;
import org.junit.Test;

import static org.junit.Assert.*;

public class CandleStickLibUnitTest {

  private CandleStickLib csLib = new CandleStickLib();

  @Test
  public void test_redCandle() {
    //600446 2016-08-12 YES
    Candle candle = new CandleBuilder().open(30.60).close(31.06).high(31.15).low(30.17).build();
    assertTrue(csLib.red(candle));
  }

  @Test
  public void test_longGreenCandle() {
    //600446 2016-05-20  YES
    Candle candle = new CandleBuilder().open(27.45).close(29.50).high(29.70).low(27.09).build();
    assertTrue(csLib.longRed(candle));

    //600446 2016-08-08  NO
    Candle candle1 = new CandleBuilder().open(30.83).close(31.60).high(31.71).low(30.40).build();
    assertFalse(csLib.longRed(candle1));
  }

  @Test
  public void test_greenCandle() {
    //600446 2016-03-03 YES
    Candle candle = new CandleBuilder().open(27.29).close(26.76).high(28.12).low(26.76).build();
    assertTrue(csLib.green(candle));

    //600446 2016-03-01 YES
    Candle candle1 = new CandleBuilder().open(24.70).close(24.66).high(25.33).low(22.55).build();
    assertTrue(csLib.green(candle1));
  }

  @Test
  public void test_doji() {
    //600446 2016-05-10 YES
    Candle candle = new CandleBuilder().open(27.60).close(27.74).high(27.92).low(27.39).build();
    assertTrue(csLib.doji(candle));
    //600446 2016-06-16
    Candle candle1 = new CandleBuilder().open(33.10).close(33.07).high(33.77).low(32.72).build();
    assertTrue(csLib.doji(candle1));
    //600446 2016-06-24
    Candle candle2 = new CandleBuilder().open(35.66).close(35.83).high(36.70).low(33.80).build();
    assertTrue(csLib.doji(candle2));

    //600446 2016-06-01
    Candle candle3 = new CandleBuilder().high(32.50).open(31.38).low(31.12).close(31.70).build();
    assertFalse(csLib.doji(candle3));

    //600446 2016-04-15
    Candle candle4 = new CandleBuilder().high(36.72).open(36.42).low(35.72).close(36.05).build();
    assertFalse(csLib.doji(candle4));

    //600519 2016-05-09
    Candle candle5 = new CandleBuilder().high(247.47).open(244.85).low(243.76).close(245.53).build();
    assertTrue(csLib.doji(candle5));

    //600519 2016-03-29
    Candle candle6 = new CandleBuilder().high(245.70).open(244.33).low(239.28).close(242.88).build();
    assertTrue(csLib.doji(candle6));
    assertTrue(csLib.hammer(candle6));
  }

  @Test
  public void test_longLeggedDoji() {
    //600519 2015-06-29
    Candle candle = new CandleBuilder().high(224.30).open(217.19).low(199.20).close(215.68).build();
    assertTrue(csLib.longLeggedDoji(candle));

    //600519 2016-07-13
    Candle candle1 = new CandleBuilder().high(232.44).open(225.61).low(219.49).close(225.39).build();
    assertTrue(csLib.doji(candle1));
    assertFalse(csLib.longLeggedDoji(candle1));
  }

  @Test
  public void test_hammer() {
    //600519 2015-10-27
    Candle candle = new CandleBuilder().high(213.09).open(212.52).low(208.09).close(210.71).build();
    assertTrue(csLib.hammer(candle));

    //600519 2015-10-21
    Candle candle1 = new CandleBuilder().high(198.28).open(197.82).low(192.85).close(196.08).build();
    assertTrue(csLib.hammer(candle1));

    //600519 2015-07-06
    Candle candle2 = new CandleBuilder().high(234.34).open(234.31).low(209.43).close(222.15).build();
    assertFalse(csLib.hammer(candle2));

    //600549 2016-05-10
    Candle candle3 = new CandleBuilder().high(20.86).open(20.50).low(19.21).close(20.14).build();
    assertTrue(csLib.hammer(candle3));
  }

  @Test
  public void test_koma() {
    //600446 2015-06-10
    Candle candle = new CandleBuilder().high(71.76).open(66.21).low(63.08).close(69.17).build();
    assertTrue(csLib.koma(candle));

    //600446 2015-05-13
    Candle candle1 = new CandleBuilder().high(85.66).open(78.01).low(74.69).close(81.01).build();
    assertTrue(csLib.koma(candle1));
  }

  @Test
  public void test_tombDoji() {
    //600446 2015-09-14
    Candle candle = new CandleBuilder().high(30.78).open(30.00).low(29.82).close(30.11).build();
    assertTrue(csLib.tombDoji(candle));

    //600446 2016-04-13
    Candle candle1 = new CandleBuilder().high(37.19).open(35.60).low(35.60).close(35.65).build();
    assertTrue(csLib.tombDoji(candle1));
  }

}
