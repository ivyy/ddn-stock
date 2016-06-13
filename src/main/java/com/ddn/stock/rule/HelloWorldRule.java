package com.ddn.stock.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Hello World Rule", description = "Hello world rule for study")
public class HelloWorldRule {

  private String input;

  @Condition
  public boolean checkInput() {
    return input.equalsIgnoreCase("yes");
  }

  @Action
  public void sayHello() {
    System.out.println("Hello you are duke's friend");
  }

  public void setInput(String input) {
    this.input = input;
  }
}
