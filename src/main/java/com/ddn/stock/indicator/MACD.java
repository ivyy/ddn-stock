package com.ddn.stock.indicator;


public class MACD {

  private TimeSeries diff;
  private TimeSeries dea;
  private TimeSeries macd;

  public MACD(TimeSeries diff, TimeSeries dea) {

    this.diff = diff;
    this.dea = dea;

    TimePoint[] output = new TimePoint[diff.length()];

    for (int i = 0; i < diff.length(); i++) {
      double value = (diff.getPoints()[i].getValue() - dea.getPoints()[i].getValue()) * 2;
      output[i] = new TimePoint(diff.getPoints()[i].getDate(), value);
    }

    this.macd = new TimeSeries(output);
  }

  public TimeSeries getDiff() {
    return diff;
  }

  public TimeSeries getDea() {
    return dea;
  }

  public TimeSeries getMacd() {
    return macd;
  }

  public boolean crossoverAt(String date) {
    return this.diff.crossoverWith(this.dea, date);
  }

  public boolean deadCrossingAt(String date) {
    return this.diff.deadCrossingWith(this.dea, date);
  }
}
