package com.usmobile.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("cycle")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cycle {

  @Id
  private String id;

  @JsonIgnore
  private String mdn;

  private Date startDate;

  private Date endDate;

  @JsonIgnore
  private String userId;


}
