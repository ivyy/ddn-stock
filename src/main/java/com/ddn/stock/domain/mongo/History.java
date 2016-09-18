package com.ddn.stock.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;


@NoArgsConstructor
@Data
@Entity("history")
@Indexes({@Index(fields = {@Field(value = "code"),@Field(value = "date")},
    options = @IndexOptions(unique = true))})

// NOTE: below does not work. You have to use "options=..." syntax
// @Indexes({@Index(fields = {@Field(value = "code"),@Field(value = "date")},unique=true)
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
