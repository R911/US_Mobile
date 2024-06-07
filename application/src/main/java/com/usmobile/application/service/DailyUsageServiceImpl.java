package com.usmobile.application.service;

import com.usmobile.application.dto.CurrentCycleDailyUsageResponse;
import com.usmobile.application.dto.DailyUsageDto;
import com.usmobile.application.model.Cycle;
import com.usmobile.application.model.DailyUsage;
import com.usmobile.application.repository.DailyUsageRepository;
import com.usmobile.application.util.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

@Service
public class DailyUsageServiceImpl implements DailyUsageService {

  private final MongoTemplate mongoTemplate;

  private final SequenceGeneratorService sequenceGeneratorService;

  private final DailyUsageRepository dailyUsageRepository;

  public DailyUsageServiceImpl(MongoTemplate mongoTemplate,
      SequenceGeneratorService sequenceGeneratorService,
      DailyUsageRepository dailyUsageRepository) {
    this.mongoTemplate = mongoTemplate;
    this.sequenceGeneratorService = sequenceGeneratorService;
    this.dailyUsageRepository = dailyUsageRepository;
  }

  /**
   * Utility Method for getting the current cycle for the user
   *
   * @param userId
   * @param mdn
   * @return
   */
  private Cycle getCurrentCycle(String userId, String mdn) {
    Query query = new Query();
    Date date = new Date();

    List<Criteria> criteria = new ArrayList<>();

    criteria.add(Criteria.where("userId").is(userId));
    criteria.add(Criteria.where("mdn").is(mdn));
    criteria.add(Criteria.where("startDate").lte(date));
    criteria.add(Criteria.where("endDate").gte(date));

    query.addCriteria(new Criteria()
        .andOperator(criteria.toArray(new Criteria[0])));

    Cycle cycle = mongoTemplate.findOne(query, Cycle.class);

    return cycle;
  }

  /**
   * Service for getting current cycle daily usage for a user.
   *
   * @param userId
   * @param mdn
   * @param pageNumber
   * @param size
   * @return
   */
  @Override
  public CurrentCycleDailyUsageResponse getCurrentCycleDailyUsage(String userId, String mdn,
      int pageNumber, int size) {

    Pageable pageable = PageRequest.of(pageNumber, size);
    Cycle cycle = getCurrentCycle(userId, mdn);

    if (cycle == null) {
      return null;
    }

    Query query = new Query().with(pageable).with(Sort.by(Direction.DESC, "usageDate"));

    List<Criteria> criteria = new ArrayList<>();

    criteria.add(Criteria.where("userId").is(userId));
    criteria.add(Criteria.where("mdn").is(mdn));
    criteria.add(Criteria.where("usageDate").gte(cycle.getStartDate()).lte(cycle.getEndDate()));

    query.addCriteria(new Criteria()
        .andOperator(criteria.toArray(new Criteria[0])));

    Page<DailyUsage> page = PageableExecutionUtils.getPage(
        mongoTemplate.find(query, DailyUsage.class
        ), pageable, () -> mongoTemplate.count(query.skip(0).limit(0), DailyUsage.class));
    List<DailyUsage> dailyUsageList = page.getContent();
    CurrentCycleDailyUsageResponse currentCycleDailyUsageResponse = CurrentCycleDailyUsageResponse
        .builder()
        .dailyUsageList(dailyUsageList)
        .last(page.isLast())
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .build();
    return currentCycleDailyUsageResponse;
  }

  /**
   * Method for saving dailyUsage objects.
   *
   * @param dailyUsageDto
   * @return
   */
  @Override
  public DailyUsage create(DailyUsageDto dailyUsageDto) {
    DailyUsage dailyUsage = DailyUsage.builder()
        .id(Constants.DAILY_USAGE_ID_PREFIX + sequenceGeneratorService.getSequenceNumber(
            Constants.DAILY_USAGE_DB_SEQUENCE_NAME))
        .mdn(dailyUsageDto.getMdn())
        .userId(dailyUsageDto.getUserId())
        .usageDate(dailyUsageDto.getUsageDate())
        .usedInMb(dailyUsageDto.getUsedInMb())
        .build();

    return dailyUsageRepository.save(dailyUsage);
  }
}
