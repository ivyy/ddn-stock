package com.ddn.stock.rule.sample;

import org.easyrules.api.RulesEngine;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

public class Launcher {

  public static void main(String[] args) throws Exception {
    //create a person instance
    Person tom = new Person("Tom", 14);
    System.out.println("Tom: Hi! can I have some Vodka please?");
    //create a rules engine
    RulesEngine rulesEngine = aNewRulesEngine()
        .named("shop rules engine")
        .build();

    //register rules
    rulesEngine.registerRule(new AgeRule(tom));
    rulesEngine.registerRule(new AlcoholRule(tom));
    Thread.sleep(30000);
    //fire rules
    rulesEngine.fireRules();
  }

}