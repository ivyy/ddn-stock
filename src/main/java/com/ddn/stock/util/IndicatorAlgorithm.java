package com.ddn.stock.util;

import com.ddn.stock.domain.DataPoint;
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
        ;
      } else if (i >= n) {
        sum -= input[i - n].getValue();
        output[j++] = new DataPoint(input[i].getDate(), sum / n);
      }
    }

    return new TimeSeries(output);
  }
}
