package com.practice.mongodb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Manik Sharma
 * Service class - contains the business logic for the application to manage data in MongoDb.
 * This Class provides methods to find tasks based on id, severity and text.
 * The Class also has method to update and delete an existing task.
 *
 */
@Service
@Slf4j
public class TaskService {

	private static Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	MongoClient mongoClient;
	
	@Autowired
	MongoConverter converter;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	/**
	 * @param task
	 * @return {@link Task}	 */
	public Task addTask(Task task) {
		logger.info("Create task in MongoDb");
		String id = UUID.randomUUID().toString();
		task.setTaskId(id);
		return taskRepository.save(task);
	}
	
	/**
	 * @return List of all tasks {@link Task}
	 */
	public List<Task> getAllTasks(){
		return taskRepository.findAll();
	}
	
	/**
	 * This method returns the task based upon ID
	 * @param id
	 * @return {@link Task}
	 */
	public Task findById(String id) {
		logger.info("Get task by id from MongoDb");
		Optional<Task> res = taskRepository.findById(id);
		return res.isPresent()?res.get():null;
	}
	
	/**
	 * @param severity
	 * @return
	 */
	public List<Task> findBySeverity(String severity){
		logger.info("Get task by severity from MongoDb");
		return taskRepository.findBySeverity(severity);
	}
	
	/**
	 * This method updates the existing task and returns the updated task object
	 * @param task
	 * @return {@link Task}
	 */
	public Task updateTask(Task task) {
		logger.info("Updating task in MongoDb...");
		Task taskfromdb = findById(task.getTaskId());
		if(taskfromdb!=null) {
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
		
		return null;
	}
	
	
	/**
	 * Deletes task by id
	 * @param id
	 */
	public void deleteTaskById(String id) {
		
		taskRepository.deleteById(id);
	}
	
	/**
	 * This method searches the tasks based upon the passed text in ["taskDescription","subTasks","assignee"]
	 * @param text
	 * @return List of all {@link Task}
	 */
	public List<Task> findByText(String text){
		logger.info("Fetch all tasks by text");
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
	
	/**
	 * This method searches for all the tasks based upon the tag Description
	 * @param str
	 * @return List of all {@link Task}
	 */
	public List<Task> findByDescription(String str){
		Sort sort = Sort.by(Sort.Direction.ASC,"storyPoints");
		return taskRepository.findByDescriptionIn(str,sort);
	}
	
	/**
	 * @param count
	 */
	public void createSampleData(int count) {
		
		List<Task> tasks = DataGenerator.buildData(count);
		taskRepository.saveAll(tasks);
	}
	

	/**
	 * @param pageable
	 * @return
	 */
	public Page<Task> getTasksByPagination(Pageable pageable){
		logger.info("Get all tasks for page - ", pageable.getPageNumber());
		Sort sort = Sort.by(Sort.Direction.ASC,"createdTime");
		pageable.getSortOr(sort);
		Query query = new Query().with(pageable);
		
		List<Task> list = mongoTemplate.find(query, Task.class);
		Page<Task> tasks = PageableExecutionUtils.getPage(list, pageable,
				() -> mongoTemplate.count(query.skip(0).limit(0), Task.class));
		
		return tasks;
	}
}
