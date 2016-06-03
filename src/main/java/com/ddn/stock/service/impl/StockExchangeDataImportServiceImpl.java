package com.ddn.stock.service.impl;

import com.ddn.stock.domain.Exchange;
import com.ddn.stock.service.StockExchangeDataImportService;
import com.ddn.stock.util.YahooExchangeDataParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by chenzi on 6/1/2016.
 */
@Service
public class StockExchangeDataImportServiceImpl implements StockExchangeDataImportService {

  @Value("${stock.exchange.csvFolder}")
  private String csvFileFolder;

  @Override
  public void fromCSV() {
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
          Exchange[] exchanges = YahooExchangeDataParser.parse(stockCode, in);
          //TODO: Save it to monogodb
          System.out.println(exchanges);
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
  }
}
