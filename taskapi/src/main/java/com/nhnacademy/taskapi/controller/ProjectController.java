package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.dto.request.ProjectMakeDto;
import com.nhnacademy.taskapi.dto.request.ProjectUpdateDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    //프로젝트 가져오기
    @GetMapping
    public ResponseEntity<DefaultDto> getAllProjects(@RequestHeader("accountId") Long accountId) {
        List<ProjectDto> projects = projectService.getProjectsByAccountId(accountId);
        DefaultDto defaultDto = new DefaultDto(200, projects);
        return ResponseEntity.status(HttpStatus.CREATED).body(defaultDto);
    }
    //프로젝트 등록
    @PostMapping
    public ResponseEntity<DefaultDto> registerProject(@RequestHeader("accountId") Long accountId,
                                                      @RequestBody ProjectMakeDto projectDto) {
        projectService.saveProject(accountId, projectDto);
        DefaultDto dto = new DefaultDto(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //프로젝트 수정
    @PutMapping("/{projectId}")
    public ResponseEntity<DefaultDto> updateProject(
            @PathVariable Long projectId, // 수정할 프로젝트 ID
            @RequestBody ProjectUpdateDto projectDto, // 수정할 프로젝트 내용
            @RequestHeader("accountId") Long accountId
    ) {
        ProjectDto updatedProject = projectService.updateProject(accountId, projectId, projectDto);
        DefaultDto dto = new DefaultDto(200, updatedProject);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //프로젝트 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity<DefaultDto> deleteProject(@RequestHeader("accountId") Long accountId,
                                                    @PathVariable Long projectId) {
        projectService.deleteProject(accountId, projectId);
        DefaultDto dto = new DefaultDto(200, null);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{projectId}/members/{registerAccountId}")
    public ResponseEntity<DefaultDto> addMemberToProject(
            @PathVariable Long projectId,  // 프로젝트 ID
            @PathVariable Long registerAccountId,   // 계정 ID
            @RequestHeader("accountId") Long requestingAccountId
    ) {
        projectService.addMemberToProject(requestingAccountId, registerAccountId, projectId);
        DefaultDto dto = new DefaultDto(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}