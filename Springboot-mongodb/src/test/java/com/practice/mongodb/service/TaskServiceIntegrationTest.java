package com.practice.mongodb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.practice.mongodb.model.Task;
import com.practice.mongodb.repository.TaskRepository;

@SpringBootTest
class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    void testAddAndFindTaskById() {
        Task task = new Task();
        task.setAssignee("Integration Tester");
        task.setSeverity("Medium");
        task.setStoryPoints(3);
        task.setTaskDescription("Integration Test Description");
        task.setSubTasks(Arrays.asList("Check1", "Check2"));

        Task saved = taskService.addTask(task);
        Task found = taskService.findById(saved.getTaskId());

        assertThat(found).isNotNull();
        assertThat(found.getAssignee()).isEqualTo("Integration Tester");
        assertThat(found.getSubTasks()).contains("Check1");
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setAssignee("Old Assignee");
        task.setSeverity("Low");
        task.setStoryPoints(2);
        task.setTaskDescription("Before update");
        task.setSubTasks(Arrays.asList("Old"));

        Task saved = taskService.addTask(task);

        Task updated = new Task();
        updated.setTaskId(saved.getTaskId());
        updated.setAssignee("New Assignee");
        updated.setSeverity("High");
        updated.setStoryPoints(8);
        updated.setTaskDescription("Updated description");
        updated.setSubTasks(Arrays.asList("New"));

        Task result = taskService.updateTask(updated);

        assertThat(result).isNotNull();
        assertThat(result.getAssignee()).isEqualTo("New Assignee");
        assertThat(result.getSubTasks()).contains("Old", "New"); // Additive update
    }

    @Test
    void testDeleteTaskById() {
        Task task = new Task();
        task.setAssignee("Delete Me");
        task.setSeverity("Critical");
        task.setStoryPoints(5);
        task.setTaskDescription("To be deleted");
        task.setSubTasks(Arrays.asList("Tmp"));

        Task saved = taskService.addTask(task);
        taskService.deleteTaskById(saved.getTaskId());

        Task result = taskService.findById(saved.getTaskId());
        assertThat(result).isNull();
    }

    @Test
    void testFindBySeverity() {
        Task task1 = new Task();
        task1.setAssignee("User A");
        task1.setSeverity("High");
        task1.setStoryPoints(1);
        task1.setTaskDescription("Test 1");
        task1.setSubTasks(Arrays.asList());

        taskService.addTask(task1);

        List<Task> result = taskService.findBySeverity("High");
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGetAllTasks() {
        taskService.createSampleData(3);
        List<Task> allTasks = taskService.getAllTasks();

        assertThat(allTasks.size()).isGreaterThanOrEqualTo(3);
    }
}
