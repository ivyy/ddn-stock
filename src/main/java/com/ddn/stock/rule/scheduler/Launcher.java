package com.ddn.stock.rule.scheduler;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;

import java.util.Date;

public class Launcher {

  private static final Date NOW = new Date();

  private static final int EVERY_SECOND = 1;

  public static void main(String[] args) throws Exception {
    RulesEngine rulesEngine = RulesEngineBuilder
        .aNewRulesEngine().named("Time Rule Engine")
        .withSilentMode(true).build();

    TimeRule timeRule = new TimeRule();
    rulesEngine.registerRule(timeRule);

    RulesEngineScheduler rulesEngineScheduler = RulesEngineScheduler.getInstance();

    rulesEngineScheduler.scheduleAtWithInterval(rulesEngine, NOW, EVERY_SECOND);

    rulesEngineScheduler.start();

    System.out.println("Print enter to exit scheduler");
    System.in.read();
    rulesEngineScheduler.stop();

  }
}
