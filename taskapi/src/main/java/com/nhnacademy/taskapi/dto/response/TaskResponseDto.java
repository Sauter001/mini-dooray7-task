package com.nhnacademy.taskapi.dto.response;

import java.time.LocalDateTime;

public record TaskResponseDto(Long taskId,
                              String title,
                              String taskContent,
                              Long assigneeId,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
