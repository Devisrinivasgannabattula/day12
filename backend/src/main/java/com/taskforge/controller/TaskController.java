package com.taskforge.controller;

import com.taskforge.dto.*;
import com.taskforge.entity.TaskStatus;
import com.taskforge.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/project/{projectId}")
    public List<TaskResponse> getTasks(@PathVariable Long projectId, Authentication auth) {
        return taskService.getProjectTasks(projectId, auth.getName());
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskRequest request, Authentication auth) {
        return taskService.create(request, auth.getName());
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateStatus(@PathVariable Long taskId,
                                     @RequestBody Map<String, String> body,
                                     Authentication auth) {
        TaskStatus status = TaskStatus.valueOf(body.get("status"));
        return taskService.updateStatus(taskId, status, auth.getName());
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId, Authentication auth) {
        taskService.delete(taskId, auth.getName());
    }
}
