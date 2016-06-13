package com.ddn.stock.rule.sample;

import org.easyrules.core.BasicRule;

public class AgeRule extends BasicRule implements AgeJmxRule {

  private int adultAge = 18;

  private Person person;

  public AgeRule(Person person) {
    super("AgeRule",
        "Check if person's age is > 18 and marks the person as adult", 1);
    this.person = person;
  }

  @Override
  public boolean evaluate() {
    return person.getAge() > adultAge;
  }

  @Override
  public void execute() {
    person.setAdult(true);
    System.out.printf("Person %s has been marked as adult",
        person.getName());
  }

  @Override
  public int getAdultAge() {
    return this.adultAge;
  }

  @Override
  public void setAdultAge(int adultAge) {
    this.adultAge = adultAge;
  }
}