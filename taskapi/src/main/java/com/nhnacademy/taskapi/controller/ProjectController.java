//package com.nhnacademy.taskapi.controller;
//
//import com.nhnacademy.taskapi.entity.Project;
//import com.nhnacademy.taskapi.service.ProjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/projects")
//public class ProjectController {
//
//    @Autowired
//    ProjectService projectService;
//
//
//    @GetMapping
//    public ResponseEntity<List<Project>> getAllProjects() {
//        // 모든 프로젝트 조회
//        return ResponseEntity.ok(projectService.getAllProjects());
//    }
//
//    @PostMapping
//    public ResponseEntity<Project> createProject(@RequestBody Project project) {
//        // 프로젝트 생성
//        Project createdProject = projectService.createProject(project);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
//    }
//
//    @PutMapping("/{projectId}")
//    public ResponseEntity<Project> updateProject(
//            @PathVariable Long projectId, @RequestBody Project project) {
//
//        Project updatedProject = projectService.updateProject(projectId, project);
//        return ResponseEntity.ok(updatedProject);
//    }
//
//    @DeleteMapping("/{projectId}")
//    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
//        projectService.deleteProject(projectId);
//        return ResponseEntity.noContent().build();
//    }
//
//}
