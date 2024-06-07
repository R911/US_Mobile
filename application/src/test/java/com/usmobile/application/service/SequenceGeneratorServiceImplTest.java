package com.usmobile.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.usmobile.application.util.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class SequenceGeneratorServiceImplTest {

  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  private MongoTemplate mongoTemplate;

  @AfterEach
  void tearDown() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void sequenceGeneratorService_getSequenceNumber_returnSequenceNumber() {

    int userDBSequenceNumber_1 = sequenceGeneratorService.getSequenceNumber(
        Constants.USER_DB_SEQUENCE_NAME);
    int userDBSequenceNumber_2 = sequenceGeneratorService.getSequenceNumber(
        Constants.USER_DB_SEQUENCE_NAME);

    int cycleDBSequenceNumber_1 = sequenceGeneratorService.getSequenceNumber(
        Constants.CYCLE_DB_SEQUENCE_NAME);
    int cycleDBSequenceNumber_2 = sequenceGeneratorService.getSequenceNumber(
        Constants.CYCLE_DB_SEQUENCE_NAME);

    int dailyUsageDBSequenceNumber_1 = sequenceGeneratorService.getSequenceNumber(
        Constants.DAILY_USAGE_DB_SEQUENCE_NAME);
    int dailyUsageDBSequenceNumber_2 = sequenceGeneratorService.getSequenceNumber(
        Constants.DAILY_USAGE_DB_SEQUENCE_NAME);

    assertEquals(userDBSequenceNumber_1, 1);
    assertEquals(userDBSequenceNumber_2, 2);

    assertEquals(cycleDBSequenceNumber_1, 1);
    assertEquals(cycleDBSequenceNumber_2, 2);

    assertEquals(dailyUsageDBSequenceNumber_1, 1);
    assertEquals(dailyUsageDBSequenceNumber_2, 2);

  }
}