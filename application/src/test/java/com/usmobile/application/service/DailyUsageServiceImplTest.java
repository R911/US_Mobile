package com.usmobile.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.usmobile.application.dto.CurrentCycleDailyUsageResponse;
import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.DailyUsageDto;
import com.usmobile.application.model.DailyUsage;
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
class DailyUsageServiceImplTest {

  @Autowired
  private DailyUsageService dailyUsageService;

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
  void dailyUsageService_getCurrentCycleDailyUsage_returnPagesOfDailyUsageOfCurrentCycle() {
    LocalDate localStartDate = LocalDate.now().minusDays(20);
    Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    LocalDate localEndDate = LocalDate.now().plusDays(10);
    Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    cycleService.create(new CycleDto("1000000000", startDate, endDate, "u_1"));

    Date date = new Date();

    DailyUsage dailyUsage = dailyUsageService.create(
        new DailyUsageDto("1000000000", "u_1", date, 50));

    CurrentCycleDailyUsageResponse response = dailyUsageService.getCurrentCycleDailyUsage("u_1",
        "1000000000",
        0, 10);

    DailyUsage usage = response.getDailyUsageList().get(0);

    assertEquals(dailyUsage.getMdn(), usage.getMdn());
    assertEquals(dailyUsage.getUserId(), usage.getUserId());
    assertEquals(dailyUsage.getUsageDate(), usage.getUsageDate());
    assertEquals(dailyUsage.getUsedInMb(), usage.getUsedInMb());

  }

  @Test
  void dailyUsageService_createDailyUsage_returnCreatedDailyUsage() {
    Date date = new Date();
    DailyUsage dailyUsage = dailyUsageService.create(
        new DailyUsageDto("1000000000", "u_1", date, 50));

    assertEquals(dailyUsage.getMdn(), "1000000000");
    assertEquals(dailyUsage.getUserId(), "u_1");
    assertEquals(dailyUsage.getUsageDate(), date);
    assertEquals(dailyUsage.getUsedInMb(), 50);

  }
}