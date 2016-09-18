package com.ddn.stock.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

@Entity("stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

  @Id
  private ObjectId id;
  @Indexed(options = @IndexOptions(unique = true))
  private String code;
  //ss : Shang Hai   sz: Shen Zhen
  private String name;
  private String description;
}
