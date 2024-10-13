package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Task task;

    @BeforeEach
    void setUp() {
        // Project, Milestone 등을 세팅할 필요가 있다면 이곳에서 추가해도 됩니다.
        // 여기서는 간단하게 Task만 생성.
        task = new Task();
        task.setTaskId(1L);
        task.setTaskContent("Sample Task Content");

        // Task를 먼저 저장
        task = taskRepository.save(task);

        // 저장 후 flush를 사용해서 DB에 반영
        entityManager.flush();
    }

    @Test
    void findCommentsByTaskIdTest() {
        // given
        Comment comment1 = new Comment("This is a comment for Task", task);
        Comment comment2 = new Comment("Another comment for Task", task);

        // 댓글 저장
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        entityManager.flush();

        // when
        var comments = commentRepository.findAllByTaskTaskId(task.getTaskId());

        // then
        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getTask().getTaskId()).isEqualTo(task.getTaskId());
    }

    @Test
    void saveCommentTest() {
        // given
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
