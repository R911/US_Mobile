package com.usmobile.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.CycleHistoryResponse;
import com.usmobile.application.model.Cycle;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class CycleServiceImplTest {

  @Autowired
  private CycleService cycleService;

  @Autowired
  private MongoTemplate mongoTemplate;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void cycleService_getCycleHistoryOfGivenMdn_returnsPagesOfCycleHistory() {
    LocalDate localStartDate = LocalDate.now().minusDays(20);
    Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    LocalDate localEndDate = LocalDate.now().plusDays(10);
    Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    Cycle cycle = cycleService.create(new CycleDto("1000000000", startDate, endDate, "u_1"));

    CycleHistoryResponse cycleHistoryResponse = cycleService.getCycleHistory("u_1", "1000000000", 0,
        10);

    Cycle result = cycleHistoryResponse.getCycles().get(0);

    assertEquals(cycle.getId(), result.getId());
    assertEquals(cycle.getMdn(), result.getMdn());
    assertEquals(cycle.getStartDate(), result.getStartDate());
    assertEquals(cycle.getEndDate(), result.getEndDate());
  }

  @Test
  void cycleService_createCycle_returnsSavedCycle() {
    LocalDate localStartDate = LocalDate.now().minusDays(20);
    Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    LocalDate localEndDate = LocalDate.now().plusDays(10);
    Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    Cycle cycle = cycleService.create(new CycleDto("1000000000", startDate, endDate, "u_1"));

    assertEquals(cycle.getUserId(), "u_1");
    assertEquals(cycle.getMdn(), "1000000000");
    assertEquals(cycle.getStartDate(), startDate);
    assertEquals(cycle.getEndDate(), endDate);
  }
}