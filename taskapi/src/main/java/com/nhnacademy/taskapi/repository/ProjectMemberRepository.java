package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Account;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    boolean existsByProjectAndAccount(Project project, Account account);
}
