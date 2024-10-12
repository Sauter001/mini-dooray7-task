package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDto(@NotNull @NotBlank String title,
                      @NotNull @NotBlank String description,
                      @NotNull Long assigneeId) {
}

