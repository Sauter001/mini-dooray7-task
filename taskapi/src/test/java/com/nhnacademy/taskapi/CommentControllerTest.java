package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.controller.CommentController;
import com.nhnacademy.taskapi.dto.request.CommentPostDto;
import com.nhnacademy.taskapi.dto.request.CommentPutDto;
import com.nhnacademy.taskapi.entity.view.CommentView;
import com.nhnacademy.taskapi.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getCommentsTest() throws Exception {
        // given
        when(commentService.getComments(1L)).thenReturn(Collections.emptyList());

        // when, then
        mockMvc.perform(get("/projects/1/tasks/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    void postCommentTest() throws Exception {
        // given
        CommentPostDto postDto = new CommentPostDto("Test comment");

        // when, then
        mockMvc.perform(post("/projects/1/tasks/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskId\": 1, \"commentContent\": \"Test comment\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCommentTest() throws Exception {
        // given
        CommentPutDto putDto = new CommentPutDto("Updated comment");
        CommentView commentView = mock(CommentView.class);

        when(commentService.updateComment(1L, 1L, putDto)).thenReturn(commentView);

        // when, then
        mockMvc.perform(put("/projects/1/tasks/1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"commentContent\": \"Updated comment\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCommentTest() throws Exception {
        // when, then
        mockMvc.perform(delete("/projects/1/tasks/1/comments/1"))
                .andExpect(status().isNoContent());
    }
}
