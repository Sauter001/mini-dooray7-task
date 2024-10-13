package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Sql("comment-test.sql")
    @Test
    void findCommentsByTaskIdTest() {
        // given
        Long taskId = 1L;
        Task task = taskRepository.findById(taskId).orElseThrow();

        // when
        var comments = commentRepository.findAllByTaskTaskId(taskId);

        // then
        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0).getTask().getTaskId()).isEqualTo(taskId);
    }

    @Test
    void saveCommentTest() {
        Task task0 = new Task();
        task0.setTaskContent("Sample Task");
        // given
        Task task = taskRepository.save(task0);
        Comment comment = new Comment("This is a test comment", task);

        // when
        Comment savedComment = commentRepository.save(comment);

        // then
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getCommentContent()).isEqualTo("This is a test comment");
        assertThat(savedComment.getTask().getTaskId()).isEqualTo(task.getTaskId());

        entityManager.flush();
    }
}
