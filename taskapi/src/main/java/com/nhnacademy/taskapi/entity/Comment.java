package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;  // Primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;  // Many-to-one relationship with Task

    @Column(name = "comment_content", nullable = false, length = 1000)  // The actual comment content
    private String commentContent;  // Comment content

    // Constructor to initialize required fields
    public Comment(String commentContent, Task task) {
        this.commentContent = commentContent;
        this.task = task;
    }
}
