package com.usmobile.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.CycleHistoryResponse;
import com.usmobile.application.model.Cycle;
import com.usmobile.application.repository.CycleRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CycleServiceTest {

  @Mock
  private CycleRepository cycleRepository;

  private CycleServiceImpl cycleService;
  @Mock
  private MongoTemplate mongoTemplate;
  @Mock
  private SequenceGeneratorServiceImpl sequenceGeneratorService;

  private Cycle cycle;

  private CycleDto cycleDto;

  private CycleHistoryResponse cycleHistoryResponse;

  @BeforeEach
  void init() {
    cycleService = new CycleServiceImpl(mongoTemplate, cycleRepository, sequenceGeneratorService);
    LocalDate localStartDate = LocalDate.now().minusDays(20);
    Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    LocalDate localEndDate = LocalDate.now().plusDays(10);
    Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    cycle = Cycle.builder()
        .mdn("1000000000")
        .startDate(startDate)
        .endDate(endDate)
        .userId("u_1")
        .id("cyc_0")
        .build();

    cycleDto = new CycleDto("1000000000", startDate, endDate, "u_1");
    List<Cycle> cycles = new ArrayList<>();
    cycles.add(cycle);

    cycleHistoryResponse = CycleHistoryResponse.builder().cycles(cycles).build();
  }

  @Test
  void cycleService_createCycle_returnCreatedCycle() {
    mock(CycleRepository.class);
    mock(Cycle.class);
    mock(CycleDto.class);
    mock(SequenceGeneratorServiceImpl.class);

    when(cycleRepository.save(cycle)).thenReturn(cycle);

    assertThat(cycleService.create(cycleDto)).isEqualTo(cycle);

  }

  @Test
  void cycleService_getCycleHistoryOfGivenMdn_returnsPagesOfCycleHistory() {

  }
}
