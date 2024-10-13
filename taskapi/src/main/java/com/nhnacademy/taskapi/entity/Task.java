package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mile_stone_id", nullable = false)
    private Milestone milestone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String taskContent;

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
