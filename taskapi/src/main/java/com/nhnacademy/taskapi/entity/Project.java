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

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account manager;

    @Setter
    private String projectName;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project")
    private List<Milestone> milestones;

    public Project(Account manager, String projectName, ProjectStatus projectStatus) {
        this.manager = manager;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
    }
}

