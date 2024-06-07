package com.usmobile.application.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CycleDto {

  private String mdn;
  private Date startDate;
  private Date endDate;
  private String userId;
}
