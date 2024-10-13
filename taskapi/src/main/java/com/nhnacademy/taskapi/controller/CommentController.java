package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.CommentPostDto;
import com.nhnacademy.taskapi.dto.request.CommentPutDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.entity.view.CommentView;
import com.nhnacademy.taskapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // GET /projects/{projectId}/tasks/comments
    @GetMapping("/{projectId}/tasks/comments")
    public ResponseEntity<DefaultDto> getComments(@PathVariable("projectId") Long projectId) {
        List<CommentView> comments = commentService.getComments(projectId);
        DefaultDto dto = new DefaultDto(200, comments);
        return ResponseEntity.ok(dto);
    }

    // POST /projects/{projectId}/tasks/comments
    @PostMapping("/{projectId}/tasks/comments")
    public ResponseEntity<DefaultDto> postComment(@RequestBody CommentPostDto postDto) {
        commentService.postComment(postDto);
        DefaultDto dto = new DefaultDto(201, null);
        return ResponseEntity.status(201).body(dto);
    }

    // PUT /projects/{projectId}/tasks/{taskId}/comments/{commentId}
    @PutMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<DefaultDto> updateComment(@PathVariable Long taskId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody CommentPutDto putDto) {
        CommentView commentView = commentService.updateComment(taskId, commentId, putDto);
        return ResponseEntity.ok(new DefaultDto(200, commentView));
    }

    // DELETE /projects/{projectId}/tasks/{taskId}/comments/{commentId}
    @DeleteMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<DefaultDto> deleteComment(@PathVariable Long taskId,
                                                    @PathVariable Long commentId) {
        commentService.deleteComment(taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}
