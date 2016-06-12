package com.ddn.stock.rule.sample;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.core.BasicRule;

public class AlcoholRule extends BasicRule {

  private Person person;

  public AlcoholRule(Person person) {
    super("AlcoholRule",
        "Children are not allowed to buy alcohol",
        2);
    this.person = person;
  }

  public boolean evaluate() {
    return !person.isAdult();
  }

  public void execute(){
    System.out.printf("Shop: Sorry %s, you are not allowed to buy alcohol",
        person.getName());
  }

}
