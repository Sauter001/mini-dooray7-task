package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false, length = 30)
    private String tagName;

    // Getters and Setters
}

