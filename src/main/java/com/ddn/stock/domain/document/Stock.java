package com.ddn.stock.domain.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.Date;

@Entity("Stock")
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Stock {

  @Id
  private ObjectId id;
  @Indexed(options = @IndexOptions(unique = true))
  private String code;
  private Date initialDate;
  private String name;
  private String description;
}
