package com.taskforge.dto;

import com.taskforge.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest(
    @NotBlank @Size(max = 200) String title,
    @Size(max = 1500) String description,
    TaskStatus status,
    LocalDate dueDate,
    @NotNull Long projectId
) {}
