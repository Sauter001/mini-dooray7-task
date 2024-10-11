package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MileStone")
public class MileStone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mileStoneId;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false, length = 255)
    private String progress;

    // Getters and Setters
}

