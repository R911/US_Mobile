package com.usmobile.application.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.usmobile.application.model.Cycle;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class CycleRepositoryTest {

  @Autowired
  private CycleRepository cycleRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @AfterEach
  void tearDown() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void cycleRepository_saveCycle_returnSavedCycle() {
    LocalDate localStartDate = LocalDate.now().minusDays(20);
    Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    LocalDate localEndDate = LocalDate.now().plusDays(10);
    Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    Cycle cycle = Cycle.builder()
        .id("cyc_1")
        .mdn("1000000000")
        .userId("u_123")
        .startDate(startDate)
        .endDate(endDate)
        .build();

    Cycle savedCycle = cycleRepository.save(cycle);

    assertEquals(cycle.getId(), savedCycle.getId());
    assertEquals(cycle.getMdn(), savedCycle.getMdn());
    assertEquals(cycle.getUserId(), savedCycle.getUserId());
    assertEquals(cycle.getStartDate(), savedCycle.getStartDate());
    assertEquals(cycle.getEndDate(), savedCycle.getEndDate());
  }


}