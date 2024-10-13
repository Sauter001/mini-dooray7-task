package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long projectId, @RequestHeader("accountId") String accountId) {
        try {
            Long parsedAccountId = Long.parseLong(accountId);
            List<Task> tasks = taskService.getTasksByProjectId(projectId, parsedAccountId);
            return ResponseEntity.ok(tasks);
        } catch (NumberFormatException e) {
            // 만약 long으로 변환되지 않는 경우..
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Long projectId, @RequestHeader("accountId") String accountId, @RequestBody Task task) {
        Task createdTask = taskService.createTask(projectId, Long.parseLong(accountId), task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestHeader("accountId") String accountId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(projectId, taskId, Long.parseLong(accountId), task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestHeader("accountId") String accountId) {
        taskService.deleteTask(projectId, taskId, Long.parseLong(accountId));
        return ResponseEntity.noContent().build();
    }
}
