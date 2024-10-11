package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, length = 30)
    private String projectName;

    @Column(nullable = false, length = 20)
    private String projectState;

    // Getters and Setters
}

