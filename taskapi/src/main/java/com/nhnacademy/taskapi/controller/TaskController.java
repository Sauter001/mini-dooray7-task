package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.TaskPostDto;
import com.nhnacademy.taskapi.dto.request.TaskPutDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.dto.response.TaskResponseDto;
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
    public ResponseEntity<DefaultDto> getTasks(@PathVariable Long projectId, @RequestHeader("accountId") String accountId) {
        try {
            Long parsedAccountId = Long.parseLong(accountId);
            // Task 리스트를 TaskResponseDto 리스트로 변환
            List<TaskResponseDto> tasks = taskService.getTasksByProjectId(projectId, parsedAccountId);
            DefaultDto dto = new DefaultDto(200, tasks);
            return ResponseEntity.ok(dto);
        } catch (NumberFormatException e) {
            // 만약 long으로 변환되지 않는 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultDto(400, "Invalid account ID"));
        }
    }


    @PostMapping
    public ResponseEntity<DefaultDto> createTask(@PathVariable Long projectId, @RequestHeader("accountId") String accountId, @RequestBody TaskPostDto taskPostDto) {
        TaskResponseDto createdTask = taskService.createTask(projectId, Long.parseLong(accountId), taskPostDto);
        DefaultDto dto = new DefaultDto(201, createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<DefaultDto> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestHeader("accountId") String accountId, @RequestBody TaskPutDto taskPutDto) {
        TaskResponseDto updatedTask = taskService.updateTask(projectId, taskId, Long.parseLong(accountId), taskPutDto);
        DefaultDto dto = new DefaultDto(200, updatedTask);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<DefaultDto> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestHeader("accountId") String accountId) {
        taskService.deleteTask(projectId, taskId, Long.parseLong(accountId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DefaultDto(204, "Task deleted successfully"));
    }
}
