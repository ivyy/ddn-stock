package com.ddn.stock.service;

public interface PositionManager {
  public void buy(int lot);
  public void sell(int lot);
  public void clear();
}
