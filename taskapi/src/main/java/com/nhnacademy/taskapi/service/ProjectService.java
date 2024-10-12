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
        Project project = new Project();
        project.setName(projectDto.projectName());
        project.setProjectStatus(ProjectStatus.ACTIVE);
        projectRepository.save(project);

        // 2. 프로젝트 생성자를 관리자로= ProjectMember에 추가
        Account adminAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        ProjectMember adminMember = new ProjectMember();
        adminMember.setProject(project);
        adminMember.setAccount(adminAccount);
        adminMember.setProjectAuth(ProjectAuth.ADMIN);  // 관리자 역할 설정
        projectMemberRepository.save(adminMember);
    }

    public Project updateProject(Long accountId, ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.projectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectDto.projectId()));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));

        // 2. 요청한 사용자가 프로젝트 멤버인지 확인
        ProjectMember projectMember =
                projectMemberRepository.findByProjectAndAccount(project, account).orElseThrow(() -> new AccountNotMemberException(accountId));

        // 3. 프로젝트 필드 업데이트
        project.setName(projectDto.projectName());
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
        if (!projectMemberRepository.existsByProjectAndAccount(project, account)){
            throw new AccountNotMemberException(accountId);
        }

        projectMemberRepository.deleteByProject(project);
        projectRepository.delete(project);
    }
    @Transactional
    public void addMemberToProject(Long accountId, Long registerAccountId, Long projectId) {
        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));
        // 프로젝트 유저인지 확인
        ProjectMember projectMember =
                projectMemberRepository.findByProjectAndAccount(project, account).orElseThrow(() -> new AccountNotMemberException(accountId));

        //account 안에 있는지 확인
        Account registerAccount = accountRepository.findById(registerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + registerAccountId));

        //프로젝트 멤버에 이미 있는지 확인
        if (projectMemberRepository.existsByProjectAndAccount(project, registerAccount)) {
            throw new IllegalArgumentException("This account is already a member of the project.");
        }
        //프로젝트멤버 추가
        ProjectMember newProjectMember = new ProjectMember();
        newProjectMember.setProject(project);
        newProjectMember.setAccount(account);
        newProjectMember.setProjectAuth(ProjectAuth.MEMBER);
        projectMemberRepository.save(projectMember);
    }

    // 사용자가 속한 프로젝트 목록 조회
    public List<Project> getProjectsByAccountId(Long accountId) {
        return projectMemberRepository.findProjectsByAccountId(accountId);
    }



}

