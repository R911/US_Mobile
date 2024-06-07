package com.usmobile.application.controller;

import com.usmobile.application.dto.CurrentCycleDailyUsageResponse;
import com.usmobile.application.dto.DailyUsageDto;
import com.usmobile.application.model.DailyUsage;
import com.usmobile.application.service.DailyUsageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * DailyUSage Controller
 */
@RestController
@RequestMapping("/dailyUsage")
public class DailyUsageController {

  private final DailyUsageService dailyUsageService;

  public DailyUsageController(DailyUsageService dailyUsageService) {
    this.dailyUsageService = dailyUsageService;
  }

  /**
   * Endpoint for creating daily usage entries in MongoDB
   *
   * @param dailyUsage dto for creating daily usage entity
   * @return DailyUsage created daily usage object
   */
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody ResponseEntity<DailyUsage> createDailyUsage(
      @RequestBody DailyUsageDto dailyUsage) {
    return new ResponseEntity<>(dailyUsageService.create(dailyUsage), HttpStatus.CREATED);
  }

  /**
   * Endpoint for getting the current cycle daily usage for a user. Supports pagination.
   *
   * @param userId of user
   * @param mdn    phone number of user
   * @param page   page number of result
   * @param size   number of records per page
   * @return CurrentCycleDailyUsageResponse
   */
  @GetMapping("/getCurrentCycle")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<CurrentCycleDailyUsageResponse> getCurrentCycleDailyUsage(
      @RequestParam String userId,
      @RequestParam String mdn, @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "32") Integer size) {
    return new ResponseEntity<>(
        dailyUsageService.getCurrentCycleDailyUsage(userId, mdn, page, size), HttpStatus.OK);
  }
}
