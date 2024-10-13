package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TagDto(Long tagId, @NotNull @NotBlank String tagName) {
}
