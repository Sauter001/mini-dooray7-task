package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Setter
    private String projectName;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project")
    private List<Milestone> milestones;

    public Project(String projectName, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.projectStatus = projectStatus;
    }
}

