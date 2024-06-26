package com.usmobile.application.controller;

import com.usmobile.application.util.DBBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dev Controller for running DB fill job. Don't use in Prod.
 */
@RestController
@RequestMapping("/dev")
public class DevController {

  private final DBBuilder dbBuilder;

  public DevController(DBBuilder dbBuilder) {
    this.dbBuilder = dbBuilder;
  }

  @GetMapping("/buildDB")
  @ResponseStatus(HttpStatus.OK)
  public void createDB() {
    dbBuilder.run();
  }

}
