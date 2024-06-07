package com.usmobile.application.service;

import com.usmobile.application.dto.CurrentCycleDailyUsageResponse;
import com.usmobile.application.dto.DailyUsageDto;
import com.usmobile.application.model.DailyUsage;

public interface DailyUsageService {

  CurrentCycleDailyUsageResponse getCurrentCycleDailyUsage(String userId, String mdn, int page,
      int size);

  DailyUsage create(DailyUsageDto dailyUsage);
}
