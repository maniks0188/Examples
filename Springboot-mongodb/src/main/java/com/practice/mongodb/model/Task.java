package com.practice.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
public class Task {

	@Id
	private String taskId;
	private String taskDescription;
	private String severity;
	private String assignee;
	private int storyPoints;
	private List<String> subTasks;
	private long createdTime;
}
