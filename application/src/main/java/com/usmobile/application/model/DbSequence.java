package com.usmobile.application.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("db_sequence")
@Data
@Builder
public class DbSequence {

  @Id
  private String id;
  private int sequence;
}
