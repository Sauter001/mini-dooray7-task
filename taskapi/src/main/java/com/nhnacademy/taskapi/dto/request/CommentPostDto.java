package com.nhnacademy.taskapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostDto {
    private String content;
    private Long taskId;
}
