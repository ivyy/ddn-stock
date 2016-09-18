package com.ddn.stock.filter;

import com.ddn.stock.domain.mongo.Stock;
import org.springframework.stereotype.Component;

@Component
public class KDJCrossingOverFilter extends StockFilter {

  /**
   * check if the KDJ indicator cross over at the date
   * and d < 30
   * @param stock
   * @param date
   * @return
   */
  @Override
  public boolean checkAtDate(Stock stock, String date) {
    return false;
  }
}
