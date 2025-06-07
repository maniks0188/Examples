package com.practice.mongodb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.mongodb.client.MongoClient;
import com.practice.mongodb.model.Task;
import com.practice.mongodb.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MongoClient mongoClient;

    @Mock
    private MongoConverter converter;

    @Mock
    private MongoTemplate mongoTemplate;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTaskId("123");
        task.setAssignee("John Doe");
        task.setSeverity("High");
        task.setStoryPoints(5);
        task.setTaskDescription("Test Description");
        task.setSubTasks(new ArrayList<>(Arrays.asList("Sub1", "Sub2")));
    }

    @Test
    void testAddTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.addTask(task);

        assertNotNull(result);
        assertEquals("John Doe", result.getAssignee());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getAllTasks();

        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
    }

    @Test
    void testFindById() {
        when(taskRepository.findById("123")).thenReturn(Optional.of(task));

        Task result = taskService.findById("123");

        assertNotNull(result);
        assertEquals("123", result.getTaskId());
    }

    @Test
    void testFindBySeverity() {
        when(taskRepository.findBySeverity("High")).thenReturn(Collections.singletonList(task));

        List<Task> results = taskService.findBySeverity("High");

        assertEquals(1, results.size());
        assertEquals("High", results.get(0).getSeverity());
    }

    @Test
    void testUpdateTask() {
        Task updated = new Task();
        updated.setTaskId("123");
        updated.setAssignee("Jane Doe");
        updated.setSeverity("Low");
        updated.setStoryPoints(8);
        updated.setTaskDescription("Updated description");
        updated.setSubTasks(new ArrayList<>(Arrays.asList("Sub3")));

        when(taskRepository.findById("123")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updated);

        Task result = taskService.updateTask(updated);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getAssignee());
        assertTrue(result.getSubTasks().contains("Sub3"));
    }

    @Test
    void testDeleteTaskById() {
        doNothing().when(taskRepository).deleteById("123");

        taskService.deleteTaskById("123");

        verify(taskRepository, times(1)).deleteById("123");
    }
}
