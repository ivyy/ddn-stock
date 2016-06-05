package com.ddn.stock.domain;


public class MACD {

  private TimeSeries diff;
  private TimeSeries dea;
  private TimeSeries macd;

  private static final double ERROR = 0.001;

  public MACD(TimeSeries diff, TimeSeries dea) {

    this.diff = diff;
    this.dea = dea;

    DataPoint[] output = new DataPoint[diff.length()];

    for (int i = 0; i < diff.length(); i++) {
      double value = (diff.getPoints()[i].getValue() - dea.getPoints()[i].getValue()) * 2;
      output[i] = new DataPoint(diff.getPoints()[i].getDate(), value);
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
    double diffValue = diff.valueAt(date);
    double deaValue = diff.valueAt(date);

    int i = diff.indexOfDate(date);
    if (i > 0) {
      return diff.getPoints()[i - 1].getValue() <= dea.getPoints()[i - 1].getValue()
          && (diffValue >= deaValue);
    }

    return false;
  }

  public boolean deadCrossingAt(String date) {

    double diffValue = diff.valueAt(date);
    double deaValue = diff.valueAt(date);

    int i = diff.indexOfDate(date);
    if (i > 0) {
      return diff.getPoints()[i - 1].getValue() >= dea.getPoints()[i - 1].getValue()
          && (diffValue <= deaValue);
    }

    return false;
  }
}
