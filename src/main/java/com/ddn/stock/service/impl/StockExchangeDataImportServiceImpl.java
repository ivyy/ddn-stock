package com.ddn.stock.service.impl;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.service.StockExchangeDataImportService;
import com.ddn.stock.service.YahooStockExchangeDataService;
import com.ddn.stock.util.YahooExchangeDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * Created by chenzi on 6/1/2016.
 */
@Service
public class StockExchangeDataImportServiceImpl implements StockExchangeDataImportService {

  @Value("${stock.exchange.csvFolder}")
  private String csvFileFolder;

  @Value("${stock.samples}")
  private String stockCodeList;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private YahooStockExchangeDataService yahooStockExchangeDataService;

  @Override
  public void fromLocalCSV() {
    mongoTemplate.dropCollection(Exchange.class);
    File file = new File(csvFileFolder);
    if (file.exists() && file.isDirectory()) {
      File[] csvFiles = file.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.endsWith(".csv");
        }
      });

      for (File csvFile : csvFiles) {
        try (InputStream in = new FileInputStream(csvFile)) {
          String stockCode = csvFile.getName().replace(".csv", "");
          List<Exchange> exchanges = YahooExchangeDataParser.parse(stockCode, in);
          //TODO: Save it to monogodb
          mongoTemplate.insert(exchanges, Exchange.class);
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
  }

  @Override
  public void fromYahoo() {
    mongoTemplate.dropCollection(Exchange.class);
    for (String stockCode:stockCodeList.trim().split(",")) {
      List<Exchange> exchanges = yahooStockExchangeDataService.getAllHistoricalData(stockCode);
      mongoTemplate.insert(exchanges, Exchange.class);
    }

  }
}
