package com.ddn.stock.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("history")
@NoArgsConstructor
@Data
public class History {
  @Id
  private ObjectId id;

  private String code;

  private String date;

  private double open;

  private double high;

  private double low;

  private double close;

  private double volume;

}
