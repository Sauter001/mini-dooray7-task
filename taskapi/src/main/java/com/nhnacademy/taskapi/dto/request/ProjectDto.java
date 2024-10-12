package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectDto(@NotNull @NotBlank String name,
                         @NotNull @NotBlank String description) {
}
