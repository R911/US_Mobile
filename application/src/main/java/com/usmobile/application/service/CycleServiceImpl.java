package com.usmobile.application.service;

import com.usmobile.application.dto.CycleDto;
import com.usmobile.application.dto.CycleHistoryResponse;
import com.usmobile.application.model.Cycle;
import com.usmobile.application.repository.CycleRepository;
import com.usmobile.application.util.Constants;
import java.util.ArrayList;
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
public class CycleServiceImpl implements CycleService {

  private final MongoTemplate mongoTemplate;

  private final SequenceGeneratorService sequenceGeneratorService;

  private final CycleRepository cycleRepository;

  public CycleServiceImpl(MongoTemplate mongoTemplate, CycleRepository cycleRepository,
      SequenceGeneratorService sequenceGeneratorService) {
    this.mongoTemplate = mongoTemplate;
    this.cycleRepository = cycleRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  /**
   * Service for getting the cycle history of a user with mdn and userid
   *
   * @param userId
   * @param mdn
   * @param pageNumber
   * @param size
   * @return CycleHistoryResponse
   */
  @Override
  public CycleHistoryResponse getCycleHistory(String userId, String mdn, int pageNumber, int size) {
    Pageable pageable = PageRequest.of(pageNumber, size);
    Query query = new Query().with(pageable).with(Sort.by(Direction.DESC, "startDate"));

    List<Criteria> criteria = new ArrayList<>();

    criteria.add(Criteria.where("userId").is(userId));
    criteria.add(Criteria.where("mdn").is(mdn));

    query.addCriteria(new Criteria()
        .andOperator(criteria.toArray(new Criteria[0])));

    Page<Cycle> page = PageableExecutionUtils.getPage(
        mongoTemplate.find(query, Cycle.class
        ), pageable, () -> mongoTemplate.count(query.skip(0).limit(0), Cycle.class));
    List<Cycle> cycles = page.toList();

    CycleHistoryResponse cycleHistoryResponse = CycleHistoryResponse.builder()
        .cycles(cycles)
        .last(page.isLast())
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .build();

    return cycleHistoryResponse;
  }

  /**
   * Service for saving Cycle objects.
   *
   * @param cycleDto
   * @return
   */
  @Override
  public Cycle create(CycleDto cycleDto) {
    Cycle cycle = Cycle.builder()
        .id(Constants.CYCLE_ID_PREFIX + sequenceGeneratorService.getSequenceNumber(
            Constants.CYCLE_DB_SEQUENCE_NAME))
        .mdn(cycleDto.getMdn())
        .userId(cycleDto.getUserId())
        .startDate(cycleDto.getStartDate())
        .endDate(cycleDto.getEndDate())
        .build();

    return cycleRepository.save(cycle);
  }
}
