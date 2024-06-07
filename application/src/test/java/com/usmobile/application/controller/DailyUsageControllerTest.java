package com.usmobile.application.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmobile.application.dto.CurrentCycleDailyUsageResponse;
import com.usmobile.application.dto.DailyUsageDto;
import com.usmobile.application.model.DailyUsage;
import com.usmobile.application.service.DailyUsageService;
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

@WebMvcTest(controllers = DailyUsageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DailyUsageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DailyUsageService dailyUsageService;

  @Autowired
  private ObjectMapper objectMapper;

  private DailyUsageDto dailyUsageDto;

  private DailyUsage dailyUsage;

  private CurrentCycleDailyUsageResponse currentCycleDailyUsageResponse;

  @BeforeEach
  void init() {
    Date date = new Date();

    dailyUsageDto = new DailyUsageDto("1000000000", "u_1", date, 50);
    dailyUsage = new DailyUsage("u_1", "1000000000", "u_1", date, 50);
    List<DailyUsage> dailyUsageList = new ArrayList<>();
    dailyUsageList.add(dailyUsage);
    currentCycleDailyUsageResponse = CurrentCycleDailyUsageResponse.builder()
        .dailyUsageList(dailyUsageList).build();

  }


  @Test
  void dailyUsageController_create_returnCreatedDailyUsage() throws Exception {
    when(dailyUsageService.create(dailyUsageDto)).thenReturn(dailyUsage);

    mockMvc.perform(post("/dailyUsage/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dailyUsageDto)))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(dailyUsage.getId())));
  }

  @Test
  void dailyUsageController_getCurrentDailyUsage_returnCurrentDailyUsage() throws Exception {
    when(dailyUsageService.getCurrentCycleDailyUsage("u_1", "1000000000", 0, 5)).thenReturn(
        currentCycleDailyUsageResponse);
    mockMvc.perform(get("/dailyUsage/getCurrentCycle").param("userId", "u_1")
            .param("mdn", "1000000000").param("page", "0").param("size", "5"))
        .andDo(print())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.dailyUsageList.[0].id",
                CoreMatchers.is(dailyUsage.getId())))
        .andExpect(status().isOk());
  }
}