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
public class DailyUsageDto {

  private String mdn;
  private String userId;
  private Date usageDate;
  private int usedInMb;
}
