package com.ddn.stock.util;

import com.ddn.stock.domain.DataPoint;
import com.ddn.stock.domain.MACD;
import com.ddn.stock.domain.MACDParam;
import com.ddn.stock.domain.TimeSeries;
import org.springframework.stereotype.Service;

@Service
public class IndicatorAlgorithm {

  public TimeSeries sma(TimeSeries series, int n) {
    if (n < 5) {
      throw new IllegalArgumentException("moving average range must be larger than 5");
    }

    int length = series.length();
    if (length < n) {
      return TimeSeries.EMPTY_SERIAL;
    }

    DataPoint[] input = series.getPoints();
    DataPoint[] output = new DataPoint[length - n + 1];

    float sum = 0f;
    int i = 0, j = 0;

    for (i = 0; i < length; i++) {
      sum += input[i].getValue();
      if (i == n - 1) {
        output[j++] = new DataPoint(input[i].getDate(), sum / n);
      } else if (i >= n) {
        sum -= input[i - n].getValue();
        output[j++] = new DataPoint(input[i].getDate(), sum / n);
      }
    }

    return new TimeSeries(output);
  }

  public TimeSeries ema(TimeSeries timeSeries, int n) {

    if (n < 1) {
      return TimeSeries.EMPTY_SERIAL;
    }

    int length = timeSeries.length();

    DataPoint[] input = timeSeries.getPoints();
    DataPoint[] output = new DataPoint[length];

    float a = 2.0f / (n + 1);
    int i = 0;

    for (i = 0; i < length; i++) {
      if (i == 0) {
        output[i] = input[i];
      } else {
        output[i] = new DataPoint(input[i].getDate(),
            a * (input[i].getValue() - output[i - 1].getValue()) + output[i - 1].getValue());
      }
    }

    return new TimeSeries(output);
  }

  public MACD macd(TimeSeries timeSeries, MACDParam macdParam) {

    DataPoint[] diff = new DataPoint[timeSeries.length()];

    DataPoint[] low = ema(timeSeries, macdParam.getLow()).getPoints();
    DataPoint[] high = ema(timeSeries, macdParam.getHigh()).getPoints();

    for (int i = 0; i < timeSeries.length(); i++) {
      diff[i] = new DataPoint(low[i].getDate(), low[i].getValue() - high[i].getValue());
    }

    TimeSeries diffTimeSeries = new TimeSeries(diff);
    TimeSeries deaTimeSeries = ema(diffTimeSeries, macdParam.getMiddle());

    return new MACD(diffTimeSeries, deaTimeSeries);
  }

}
