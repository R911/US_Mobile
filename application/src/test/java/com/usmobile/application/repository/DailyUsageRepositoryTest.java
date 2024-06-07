package com.usmobile.application.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.usmobile.application.model.DailyUsage;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class DailyUsageRepositoryTest {

  @Autowired
  private DailyUsageRepository dailyUsageRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @AfterEach
  void tearDown() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void dailyUsageRepository_saveDailyUsage_returnSavedDailyUsage() {
    DailyUsage dailyUsage = DailyUsage.builder()
        .id("du_1234")
        .userId("u_121")
        .mdn("1111111111")
        .usedInMb(99)
        .usageDate(new Date())
        .build();

    DailyUsage savedDailyUsage = dailyUsageRepository.save(dailyUsage);

    assertEquals(dailyUsage.getId(), savedDailyUsage.getId());
    assertEquals(dailyUsage.getMdn(), savedDailyUsage.getMdn());
    assertEquals(dailyUsage.getUsedInMb(), savedDailyUsage.getUsedInMb());
    assertEquals(dailyUsage.getUserId(), savedDailyUsage.getUserId());
    assertEquals(dailyUsage.getUsageDate(), savedDailyUsage.getUsageDate());
  }
}