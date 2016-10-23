package com.ddn.stock.service.impl;

import com.ddn.stock.domain.document.PowerFactor;
import com.ddn.stock.domain.document.Stock;
import com.ddn.stock.domain.document.Tick;
import com.ddn.stock.service.ImportService;
import com.ddn.stock.service.SinaDataService;
import com.ddn.stock.service.StockService;
import com.ddn.stock.service.YahooDataService;
import com.ddn.stock.util.CommonUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * this service load data from yahoo and save it into mongodb
 */
@Service
public class ImportServiceImpl implements ImportService {

  @Autowired
  private YahooDataService yahooDataService;

  @Autowired
  private SinaDataService sinaDataService;

  @Autowired
  private StockService stockService;

  @Autowired
  private Datastore datastore;

  /*
    load all from scratch
   */
  @Override
  public void importDailyTicksFromYahoo(String stockCode) {
    List<Tick> ticks = yahooDataService.getAll(stockCode);
    datastore.delete(datastore.createQuery(Tick.class).field("stockCode").equal(stockCode));
    datastore.save(ticks);
  }

  /*
    load data between two date
   */
  @Override
  public void importDailyTicksFromYahoo(String stockCode, LocalDate startDate) {
    Stock stock = stockService.findOne(stockCode);
    if (stock == null) return;

    List<Tick> ticks = yahooDataService.getFrom(stockCode, startDate);
    Query query = datastore.createQuery(Tick.class)
        .field("stockCode").equal(stockCode)
        .field("date").greaterThanOrEq(startDate);
    datastore.delete(query);
    datastore.save(ticks);
  }

  @Override
  public void importPowerFactorFromSina(String stockCode) {
    //needs to know the initial market day of <stockCode>
    Stock stock = stockService.findOne(stockCode);
    if (stock != null) {
      LocalDate currentDate = LocalDate.now();
      int currentYear = currentDate.getYear();
      int currentQuarter = currentDate.getMonthValue() / 3 + 1;

      Date initialDate = stock.getInitialDate();
      int year = initialDate.getYear() + 1900;
      int quarter = initialDate.getMonth() / 3 + 1;

      while (year < currentYear || (year == currentYear && quarter <= currentQuarter)) {
        importPowerFactorFromSina(stockCode, year, quarter);
        quarter += 1;
        if (quarter > 4) {
          quarter = 1;
          year += 1;
        }
      }
    }
  }

  @Override
  public void importPowerFactorFromSina(String stockCode, int year, int quarter) {
    List<PowerFactor> powerFactorList =
        sinaDataService.getPowerFactor(stockCode, year, quarter);

    Date startDate = new Date(year, (quarter - 1) * 3 + 1, 1);
    Date endDate = new Date(year, quarter * 3 + 1, 1);
    Query query = datastore.createQuery(PowerFactor.class)
        .field("stockCode").equal(stockCode)
        .field("date").greaterThanOrEq(startDate)
        .field("date").lessThan(endDate);

    datastore.delete(query);
    datastore.save(powerFactorList);
  }

  @Override
  public void importFromScratch() {
    stockService.findAll().stream().forEach(stock -> {
      //import from yahoo daily ticks
      importDailyTicksFromYahoo(stock.getCode());
      //update the stock initial date
      stockService.fillInitialDate(stock.getCode());
      //import all the power factor from sina
      importPowerFactorFromSina(stock.getCode());
    });
  }
}
