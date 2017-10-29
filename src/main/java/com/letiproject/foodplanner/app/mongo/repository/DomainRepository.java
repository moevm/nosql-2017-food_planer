package com.letiproject.foodplanner.app.mongo.repository;


import com.letiproject.foodplanner.app.mongo.domain.TestObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainRepository extends MongoRepository<TestObject, Long> {
}
