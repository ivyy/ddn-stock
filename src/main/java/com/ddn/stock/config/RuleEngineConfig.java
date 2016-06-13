package com.ddn.stock.config;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleEngineConfig {

  //TODO: put easy rule engine configuration on appliction.properties and load them to construct the rule engine

  @Bean
  public RulesEngine ruleEngine() {
    return RulesEngineBuilder.aNewRulesEngine()
        .named("ddn rules engine")
        .withSilentMode(true)
        .build();
  }

}
