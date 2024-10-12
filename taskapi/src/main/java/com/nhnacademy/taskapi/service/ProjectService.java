package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.entity.Account;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectAuth;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.repository.AccountRepository;
import com.nhnacademy.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository, AccountRepository accountRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.accountRepository = accountRepository;
    }

    // 프로젝트 생성 및 관리자 설정
    public Project createProjectWithAdmin(Long adminAccountId, String projectName) {
        // 1. 프로젝트 생성
        Project project = new Project();
        project.setProjectName(projectName);
        projectRepository.save(project);

        // 2. 프로젝트 생성자를 관리자로 ProjectMember에 추가
        Account adminAccount = accountRepository.findById(adminAccountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        ProjectMember adminMember = new ProjectMember();
        adminMember.setProject(project);
        adminMember.setAccount(adminAccount);
        adminMember.setProjectAuth(ProjectAuth.ADMIN);  // 관리자 역할 설정
        projectMemberRepository.save(adminMember);

        return project;
    }

    public void addMemberToProject(Long projectId, Long accountId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (projectMemberRepository.existsByProjectAndAccount(project, account)) {
            throw new IllegalArgumentException("This account is already a member of the project");
        }

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setAccount(account);
        projectMember.setProjectAuth(ProjectAuth.MEMBER);  // 관리자 역할 설정
        projectMemberRepository.save(projectMember);
    }


}

