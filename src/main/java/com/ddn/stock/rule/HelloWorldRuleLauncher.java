package com.ddn.stock.rule;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

import java.util.Scanner;

public class HelloWorldRuleLauncher {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Are you a friend of duke?[yes/no]:");
    String input = scanner.nextLine();

    HelloWorldRule helloWorldRule = new HelloWorldRule();
    helloWorldRule.setInput("yes");

    RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();
    rulesEngine.registerRule(helloWorldRule);
    rulesEngine.fireRules();
  }
}
