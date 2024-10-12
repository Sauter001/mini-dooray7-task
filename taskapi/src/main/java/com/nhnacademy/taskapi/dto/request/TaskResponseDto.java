package com.nhnacademy.taskapi.dto.request;

import java.time.LocalDateTime;

public record TaskResponseDto(Long taskId,
                              String title,
                              String description,
                              Long assigneeId,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
