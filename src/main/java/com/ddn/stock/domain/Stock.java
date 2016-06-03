package com.ddn.stock.domain;

import lombok.Data;

public class Stock {
  private String id;
  private String code;
  //ss : Shang Hai   sz: Shen Zhen
  private String market;
  private String displayName;
  private String description;

  public Stock(String code, String market, String displayName, String description) {
    this.code = code;
    this.market = market;
    this.displayName = displayName;
    this.description = description;
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
