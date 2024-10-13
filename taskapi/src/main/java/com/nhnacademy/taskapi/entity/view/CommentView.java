package com.nhnacademy.taskapi.entity.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentView {
    private Long commentId;
    private String content;
    private Long taskId;
    private Long projectId;

    // You can add any additional fields you want to expose in the view
}
