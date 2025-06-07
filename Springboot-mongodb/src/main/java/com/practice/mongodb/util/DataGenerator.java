package com.practice.mongodb.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.practice.mongodb.model.Task;


/**
 * This class generates dummy data for the application based upon the input provided by the user.
 * @author user
 *
 */
@Component
public class DataGenerator {

	private static List<String> assignees = Arrays.asList(new String[]{"Manik","Max","John","Levis","Sara","Jena"});
	private static List<String> severity = Arrays.asList(new String[]{"Low","Medium","High","Critical"});
	
	private static String DESC_TXT = "This text is used for description of task for ";
	private static String PREFIX_SUBTASK = "Subtask - ";
	private static String SUB_TASK_DESC_TXT = " This text is used for description of sub task for ";
	
	
	/**
	 * This method generates task objects to be stored in MongoDB
	 * @param count
	 * @return List of {@link Task} objects
	 */
	public static List<Task> buildData(int count) {
		
		List<Task> tasks = new ArrayList<>();
		for(int i=0;i<count;i++) {
			Task task = new Task();
			String assignee = getAssignee();
			task.setTaskDescription(DESC_TXT+ assignee);
			task.setAssignee(assignee);
			String severity = getSeverity();
			task.setSeverity(severity);
			int sp = getStoryPoints();
			task.setStoryPoints(sp);
			task.setSubTasks(getSubTasks(sp, assignee));
			task.setCreatedTime(createdTime());
			tasks.add(task);
		}
		
		return tasks;
	}
	
	static String getAssignee() {
		Random random = new Random();
		return assignees.get(random.nextInt(assignees.size()));
	}
	
	static String getSeverity() {
		Random random = new Random();
		return severity.get(random.nextInt(severity.size()));
	}
	
	static Integer getStoryPoints() {
		Random random = new Random();
		return random.nextInt(1, 8);
	}
	
	static List<String> getSubTasks(int c,String assignee){
		List<String> subTasks = new ArrayList<String>();
		StringBuilder sb = null;
		for(int i=1;i<=c;i++) {
			sb = new StringBuilder();
			sb.append(PREFIX_SUBTASK);
			sb.append(i);
			sb.append(SUB_TASK_DESC_TXT);
			sb.append(assignee);
			
			subTasks.add(sb.toString());
		}
		
		return subTasks;
	}
	
	static long createdTime() {
		return Instant.now().toEpochMilli();
	}
}
