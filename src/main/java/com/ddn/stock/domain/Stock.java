package com.ddn.stock.domain;

import java.util.List;
import java.util.Map;

public class Stock {
  private String id;
  private String code;
  //ss : Shang Hai   sz: Shen Zhen
  private String market;
  private String displayName;
  private String description;

  //the daily exchange
  private List<Exchange> exchangeList;
  private Map<String, Object> indicators;

  public Stock(String code, String market, String displayName, String description) {
    this.code = code;
    this.market = market;
    this.displayName = displayName;
    this.description = description;
  }

  public Map<String, Object> getIndicators() {
    return indicators;
  }

  public List<Exchange> getExchangeList() {
    return exchangeList;
  }

  public void setExchangeList(List<Exchange> exchangeList) {
    this.exchangeList = exchangeList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Stock{" +
        "id='" + id + '\'' +
        ", code='" + code + '\'' +
        ", market='" + market + '\'' +
        ", displayName='" + displayName + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
