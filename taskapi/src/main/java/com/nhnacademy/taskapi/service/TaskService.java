package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//에러 일단은 모두 runtimeexception으로 처리
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    // 프로젝트 ID로 프로젝트에 속한 모든 task 조회
    public List<Task> getTasksByProjectId(Long projectId, Long accountId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return taskRepository.findByProject(project);
    }

    // Task 생성
    public Task createTask(Long projectId, Long accountId, Task task) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(project);
        return taskRepository.save(task);
    }

    // Task 수정

    public Task updateTask(Long projectId, Long taskId, Long accountId, Task taskDetails) {
        // 1. 먼저 프로젝트 존재 여부 확인
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 2. Task 존재 여부 확인
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 3. Task가 해당 프로젝트에 속하는지 확인
        if (!task.getProject().getProjectId().equals(projectId)) {
            throw new RuntimeException("Task does not belong to the specified project");
        }

        // Task content 업데이트
        task.setTaskContent(taskDetails.getTaskContent());
        return taskRepository.save(task);
    }

    // Task 삭제
    public void deleteTask(Long projectId, Long taskId, Long accountId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getProject().getProjectId().equals(projectId)) {
            throw new RuntimeException("Task does not belong to the specified project");
        }
        taskRepository.delete(task);
    }
}
