package com.ddn.stock.config;

import com.ddn.stock.util.IndicatorAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public IndicatorAlgorithm indicatorAlgorithm() {
    return new IndicatorAlgorithm();
  }

}
