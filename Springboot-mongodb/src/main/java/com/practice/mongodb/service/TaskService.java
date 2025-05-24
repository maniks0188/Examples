package com.practice.mongodb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.practice.mongodb.model.Task;
import com.practice.mongodb.repository.TaskRepository;
import com.practice.mongodb.util.DataGenerator;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	MongoClient mongoClient;
	
	@Autowired
	MongoConverter converter;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public Task addTask(Task task) {
		String id = UUID.randomUUID().toString();
		task.setTaskId(id);
		return taskRepository.save(task);
	}
	
	public List<Task> getAllTasks(){
		return taskRepository.findAll();
	}
	
	public Task findById(String id) {
		Optional<Task> res = taskRepository.findById(id);
		return res.isPresent()?res.get():null;
	}
	
	public List<Task> findBySeverity(String severity){
		return taskRepository.findBySeverity(severity);
	}
	
	public Task updateTask(Task task) {
		
		Task taskfromdb = findById(task.getTaskId());
		taskfromdb.setAssignee(task.getAssignee());
		taskfromdb.setSeverity(task.getSeverity());
		taskfromdb.setStoryPoints(task.getStoryPoints());
		taskfromdb.setTaskDescription(task.getTaskDescription());
		if(taskfromdb.getSubTasks() == null) {
			taskfromdb.setSubTasks(new ArrayList<String>());
		}
		task.getSubTasks().stream().forEach(t -> taskfromdb.getSubTasks().add(t));

		return taskRepository.save(taskfromdb);
		
	}
	
	public void deleteTaskById(String id) {
		
		taskRepository.deleteById(id);
	}
	
	public List<Task> findByText(String text){
		
		List<Task> list = new ArrayList<Task>();
		MongoDatabase database = mongoClient.getDatabase("Task");
		MongoCollection<org.bson.Document> collection = database.getCollection("tasks");
		AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
				new Document("$search",
						new Document("index", "default").append("text",
								new Document("query", text).append("path", Arrays.asList("taskDescription","subTasks","assignee")))),
				new Document("$sort", new Document("storyPoints", 1L))));

		result.forEach(obj ->  list.add(converter.read(Task.class, obj)));
		
		return list;
	}
	
	public List<Task> findByDescription(String str){
		Sort sort = Sort.by(Sort.Direction.ASC,"storyPoints");
		return taskRepository.findByDescriptionIn(str,sort);
	}
	
	public void createSampleData(int count) {
		
		List<Task> tasks = DataGenerator.buildData(count);
		taskRepository.saveAll(tasks);
	}
	

	public Page<Task> getTasksByPagination(Pageable pageable){
		Sort sort = Sort.by(Sort.Direction.ASC,"createdTime");
		pageable.getSortOr(sort);
		Query query = new Query().with(pageable);
		
		List<Task> list = mongoTemplate.find(query, Task.class);
		Page<Task> tasks = PageableExecutionUtils.getPage(list, pageable,
				() -> mongoTemplate.count(query.skip(0).limit(0), Task.class));
		
		return tasks;
	}
}
