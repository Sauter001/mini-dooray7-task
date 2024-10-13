package com.nhnacademy.taskapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostDto {
    private String commentContent;
    private Long taskId;
}
