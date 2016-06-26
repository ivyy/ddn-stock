package com.ddn.stock.indicator;

public class KDJ {

  private TimeSeries kTimeSeries;
  private TimeSeries dTimeSeries;
  private TimeSeries jTimeSeries;

  public KDJ(TimeSeries kTimeSeries, TimeSeries dTimeSeries, TimeSeries jTimeSeries) {
    this.kTimeSeries = kTimeSeries;
    this.dTimeSeries = dTimeSeries;
    this.jTimeSeries = jTimeSeries;
  }

  public TimeSeries getkTimeSeries() {
    return kTimeSeries;
  }

  public TimeSeries getdTimeSeries() {
    return dTimeSeries;
  }

  public TimeSeries getjTimeSeries() {
    return jTimeSeries;
  }
}
