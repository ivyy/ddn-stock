package com.ddn.stock.rule.sample;

import org.easyrules.api.JmxRule;

@javax.management.MXBean
public interface AgeJmxRule extends JmxRule {

    int getAdultAge();

    void setAdultAge(int adultAge);

}