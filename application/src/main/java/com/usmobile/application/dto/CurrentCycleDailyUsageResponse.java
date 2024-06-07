package com.usmobile.application.dto;

import com.usmobile.application.model.DailyUsage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentCycleDailyUsageResponse {

  private List<DailyUsage> dailyUsageList;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;
}
