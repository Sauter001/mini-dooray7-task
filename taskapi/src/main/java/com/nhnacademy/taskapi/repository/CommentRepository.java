package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.view.CommentView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // taskId를 통해 CommentView 리스트를 조회하는 쿼리
    List<CommentView> findAllByTaskTaskId(Long taskId);

    // commentId로 CommentView를 조회하는 쿼리
    CommentView findByCommentId(Long commentId);
}