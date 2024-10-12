package com.nhnacademy.taskapi.dto.request;

import com.nhnacademy.taskapi.entity.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectUpdateDto(@NotNull @NotBlank String title,
                             @NotNull @NotBlank State projectState) {
}
