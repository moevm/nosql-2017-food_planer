package com.letiproject.foodplanner.app.repository;


import com.letiproject.foodplanner.app.domain.TestObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainRepository extends MongoRepository<TestObject, Long> {
}
