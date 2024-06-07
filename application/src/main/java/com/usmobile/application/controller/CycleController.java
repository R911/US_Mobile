package com.usmobile.application.controller;

import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.CycleHistoryResponse;
import com.usmobile.application.model.Cycle;
import com.usmobile.application.service.CycleService;
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
 * Cycle Controller
 */
@RestController
@RequestMapping("/cycle")
public class CycleController {

  private final CycleService cycleService;

  public CycleController(CycleService cycleService) {
    this.cycleService = cycleService;
  }

  /**
   * Endpoint for creating cycle in MongoDB
   *
   * @param cycle input cycleDTO object
   * @return Cycle created cycle object
   */
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody ResponseEntity<Cycle> createCycle(@RequestBody CycleDto cycle) {
    return new ResponseEntity<>(cycleService.create(cycle), HttpStatus.CREATED);
  }

  /**
   * Endpoint for getting current cycle usage history
   *
   * @param userId
   * @param mdn    phone number
   * @param page
   * @param size
   * @return CycleHistoryResponse
   */
  @GetMapping("/getHistory")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<CycleHistoryResponse> getHistory(@RequestParam String userId,
      @RequestParam String mdn,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "5") Integer size) {

    return new ResponseEntity<>(cycleService.getCycleHistory(userId, mdn, page, size),
        HttpStatus.OK);
  }
}
