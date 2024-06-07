package com.usmobile.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("daily_usage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyUsage {

  @Id
  private String id;
  @JsonIgnore
  private String mdn;
  @JsonIgnore
  private String userId;
  private Date usageDate;
  private int usedInMb;
}
