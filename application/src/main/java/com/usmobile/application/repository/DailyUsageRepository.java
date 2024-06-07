package com.usmobile.application.repository;

import com.usmobile.application.model.DailyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyUsageRepository extends MongoRepository<DailyUsage, String> {

}
