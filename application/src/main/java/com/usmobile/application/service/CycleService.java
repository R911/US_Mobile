package com.usmobile.application.service;

import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.CycleHistoryResponse;
import com.usmobile.application.model.Cycle;

public interface CycleService {

  CycleHistoryResponse getCycleHistory(String userId, String mdn, int pageNumber, int size);

  Cycle create(CycleDto cycleDto);
}
