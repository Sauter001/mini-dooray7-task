package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.entity.*;
import com.nhnacademy.taskapi.exception.AccountNotFoundException;
import com.nhnacademy.taskapi.exception.AccountNotMemberException;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.AccountRepository;
import com.nhnacademy.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
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
    public void saveProject(Long accountId, ProjectDto projectDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));

        Project project = new Project(account, projectDto.projectName(), ProjectStatus.ACTIVE);
        projectRepository.save(project);
    }

    public Project updateProject(Long accountId, ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.projectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectDto.projectId()));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));

        // 2. 요청한 사용자가 프로젝트 멤버인지 확인
        ProjectMember projectMember =
                projectMemberRepository.findByProjectAndMember(project, account).orElseThrow(() -> new AccountNotMemberException(accountId));

        // 3. 프로젝트 필드 업데이트
        project.setProjectName(projectDto.projectName());
        project.setProjectStatus(projectDto.projectStatus());

        // 4. 업데이트된 프로젝트 저장
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(long accountId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));
        if (!projectMemberRepository.existsByProjectAndMember(project, account)){
            throw new AccountNotMemberException(accountId);
        }

        projectMemberRepository.deleteByProject(project);
        projectRepository.delete(project);
    }


    public void addMemberToProject(Long accountId, Long registerAccountId, Long projectId) {
        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID:" + projectId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID: " + accountId));

        if(!(project.getManager().getAccountId().equals(accountId) || projectMemberRepository.existsByProjectAndMember(project, account))){
            throw new AccountNotMemberException(accountId);
        }

        ProjectMember projectMember =
                projectMemberRepository.findByProjectAndMember(project, account).orElseThrow(() -> new AccountNotMemberException(accountId));

        //account 안에 있는지 확인
        Account registerAccount = accountRepository.findById(registerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID: " + registerAccountId));

        //프로젝트 멤버에 이미 있는지 확인
        if (projectMemberRepository.existsByProjectAndMember(project, registerAccount)) {
            throw new IllegalArgumentException("This account is already a member of the project.");
        }
        //프로젝트멤버 추가
        ProjectMember newProjectMember = new ProjectMember(project, registerAccount);
        projectMemberRepository.save(newProjectMember);
    }

    // 사용자가 속한 프로젝트 목록 조회
    public List<Project> getProjectsByAccountId(Long accountId) {
        List<Project> projectList = new ArrayList<>();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));
        projectList.addAll(account.getProjects());
        projectList.addAll(projectMemberRepository.findProjectsByAccountId(accountId));
        return projectList;
    }
}

