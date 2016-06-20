package com.ddn.stock.service.impl;

import com.ddn.stock.service.PositionManager;
import org.springframework.stereotype.Service;

@Service
public class MockDatabasePositionManager implements PositionManager {

  @Override
  public void buy(int lot) {
    System.out.println(String.format("mock: buy %d lots", lot));
  }

  @Override
  public void sell(int lot) {
    System.out.println(String.format("mock: sell %d lots", lot));
  }

  @Override
  public void clear() {
    System.out.println("mock: clear position.");
  }
}
