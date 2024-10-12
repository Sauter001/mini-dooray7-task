package com.nhnacademy.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ProjectMember")
@Setter
@Getter
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectMemberId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)  // 외래키 명시
    private Project project;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)  // 외래키 명시
    private Account account;

    @JsonProperty("project_auth")
    private ProjectAuth projectAuth;
}

