package com.taskforge.dto;

import com.taskforge.entity.TaskStatus;
import java.time.LocalDate;

public record TaskResponse(
    Long id, String title, String description, TaskStatus status,
    LocalDate dueDate, Long projectId, String assigneeName
) {}
