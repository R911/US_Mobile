package com.usmobile.application.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import com.usmobile.application.model.DbSequence;
import java.util.Objects;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Service for implementing auto increment for various collections.
 */
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

  private final MongoOperations mongoOperations;

  public SequenceGeneratorServiceImpl(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  /**
   * Provides next int in the auto-increment order for a collection by maintaining a separate
   * collection to track the increments.
   *
   * @param sequenceName
   * @return
   */
  @Override
  public int getSequenceNumber(String sequenceName) {
    Query query = new Query(Criteria.where("id").is(sequenceName));
    //update the sequence no
    Update update = new Update().inc("sequence", 1);
    //modify in document
    DbSequence counter = mongoOperations
        .findAndModify(query,
            update, options().returnNew(true).upsert(true),
            DbSequence.class);

    return !Objects.isNull(counter) ? counter.getSequence() : 1;
  }
}
