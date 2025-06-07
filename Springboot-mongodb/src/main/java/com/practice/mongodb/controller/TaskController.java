package com.practice.mongodb.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.mongodb.model.Task;
import com.practice.mongodb.service.TaskService;

/**
 * This Rest Controller class provides endpoints for the users to manage the MongoDb application
 * @author Manik Sharma
 *
 */
@RestController
@RequestMapping(path = "/api/v1")
public class TaskController {

	@Autowired
	private TaskService service;
	
	@GetMapping(path = "/task/{id}")
	public ResponseEntity<Task> findTaskById(@PathVariable(name = "id") String taskId){
		
		Task task = service.findById(taskId);
		
		return new ResponseEntity<Task>(task, HttpStatus.FOUND);
	}
	
	@PostMapping(path = "/task")
	public ResponseEntity<Task> createTask(@RequestBody Task request){
		
		Task task = service.addTask(request);
		
		return new ResponseEntity<Task>(task,HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/tasks")
	public ResponseEntity<List<Task>> getAllTasks(){
		
		List<Task> tasks = service.getAllTasks();
		
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.FOUND);
	}
	
	@PutMapping(path = "/task")
	public ResponseEntity<Task> updateTask(@RequestBody Task request){
		
		Task task = service.updateTask(request);
		
		return new ResponseEntity<Task>(task,HttpStatus.OK);
	}
	
	@GetMapping(path = "/task/severity/{severity}")
	public ResponseEntity<List<Task>> getAllTasksBySeverity(@PathVariable String severity){
		
		List<Task> tasks = service.findBySeverity(severity);
		
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.FOUND);
	}
	
	@DeleteMapping(path = "/task/delete/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable String id){
		
		service.deleteTaskById(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/tasks/{text}")
	public ResponseEntity<List<Task>> textSearch(@PathVariable String text){
		
		List<Task> result = service.findByText(text);
		
		return new ResponseEntity<List<Task>>(result,HttpStatus.OK);
	}
	
	@GetMapping(path = "/tasks/description")
	public ResponseEntity<List<Task>> searchByDescription(@RequestParam(value = "text") String text){
		
		List<Task> result = service.findByDescription(text);
		
		return new ResponseEntity<List<Task>>(result,HttpStatus.OK);
	}
	
	@PostMapping(path = "/tasks/createdata")
	public ResponseEntity<String> createDummyData(@RequestParam(value = "size") String size){
		
		service.createSampleData(Integer.parseInt(size));
		return new ResponseEntity<String>("Created",HttpStatus.OK);
		
	}
	
	
	@GetMapping(path = "/tasks/limit")
	public ResponseEntity<Page<Task>> getTasksByPage(@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size") Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Task> res = service.getTasksByPagination(pageable);
		
		return new ResponseEntity<Page<Task>>(res,HttpStatus.OK);
	}
	
}
