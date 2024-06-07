package com.usmobile.application.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.CycleHistoryResponse;
import com.usmobile.application.model.Cycle;
import com.usmobile.application.service.CycleService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CycleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CycleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CycleService cycleService;

  @Autowired
  private ObjectMapper objectMapper;

  private Cycle cycle;

  private CycleDto cycleDto;

  private CycleHistoryResponse cycleHistoryResponse;


  @BeforeEach
  void init() {
    LocalDate localStartDate = LocalDate.now().minusDays(20);
    Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    LocalDate localEndDate = LocalDate.now().plusDays(10);
    Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    cycle = Cycle.builder().id("cyc_1").userId("u_1").mdn("1000000000").startDate(startDate)
        .endDate(endDate).build();

    cycleDto = CycleDto.builder().userId("u_1").mdn("1000000000").startDate(startDate)
        .endDate(endDate).build();

    List<Cycle> cycleList = new ArrayList<>();
    cycleList.add(cycle);

    cycleHistoryResponse = CycleHistoryResponse.builder().cycles(cycleList).build();
  }


  @Test
  void cycleController_createCycle_returnCreatedCycle() throws Exception {

    when(cycleService.create(cycleDto)).thenReturn(cycle);

    mockMvc.perform(post("/cycle/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(cycleDto)))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(cycle.getId())));

  }

  @Test
  void cycleController_getHistory_returnCycleHistoryResponse() throws Exception {
    when(cycleService.getCycleHistory("u_1", "1000000000", 0, 5)).thenReturn(cycleHistoryResponse);
    mockMvc.perform(get("/cycle/getHistory").param("userId", "u_1")
            .param("mdn", "1000000000").param("page", "0").param("size", "5"))
        .andDo(print())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.cycles.[0].id", CoreMatchers.is(cycle.getId())))
        .andExpect(status().isOk());
  }
}