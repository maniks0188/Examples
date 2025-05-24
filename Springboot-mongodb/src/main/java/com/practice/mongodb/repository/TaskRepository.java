package com.practice.mongodb.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.practice.mongodb.model.Task;

public interface TaskRepository extends MongoRepository<Task, String>{

	List<Task> findBySeverity(String severity);
	
	@Query(value = "{'taskDescription':{$regex:?0,$options:'i'}}")
	List<Task> findByDescriptionIn(String searchStr, Sort sort);

}
