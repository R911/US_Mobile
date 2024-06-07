package com.usmobile.application.util;

import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.DailyUsageDto;
import com.usmobile.application.dto.UserCreateDto;
import com.usmobile.application.service.CycleService;
import com.usmobile.application.service.DailyUsageService;
import com.usmobile.application.service.UserService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Utility Service to fill DB for first time.
 */
@Service
public class DBBuilder {

  private final Random random = new Random();
  @Autowired
  private UserService userService;
  @Autowired
  private CycleService cycleService;
  @Autowired
  private DailyUsageService dailyUsageService;

  public void run() {
    fillUsers();
    fillCycle();
    fillDailyUsage();
  }

  private void fillUsers() {

    for (int i = 1; i <= 5; i++) {
      UserCreateDto user = UserCreateDto.builder()
          .firstName("FirstName_" + i)
          .lastName("LastName_" + i)
          .email("Email_" + i + "@gmail.com")
          .password("Password@user_" + i)
          .build();
      userService.create(user);
    }


  }

  private void fillDailyUsage() {
    for (int i = 1; i <= 5; i++) {
      String mdn = i + "000000000";
      String userId = Constants.USER_ID_PREFIX + i;

      for (int j = 0; j <= 15; j++) {
        LocalDate localDate = LocalDate.now().minusDays(j);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        int usage = random.nextInt(101);
        dailyUsageService.create(new DailyUsageDto(mdn, userId, date, usage));
      }
    }
  }

  private void fillCycle() {
    for (int i = 1; i <= 5; i++) {
      String mdn = i + "000000000";
      String userId = Constants.USER_ID_PREFIX + i;
      LocalDate localStartDate = LocalDate.now().minusDays(15);
      LocalDate localEndDate = LocalDate.now().plusDays(15);

      Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

      cycleService.create(new CycleDto(mdn, startDate, endDate, userId));
    }
  }
}
