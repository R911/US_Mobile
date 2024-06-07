package com.usmobile.application.repository;

import com.usmobile.application.model.Cycle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleRepository extends MongoRepository<Cycle, String> {

}
