package com.taskforge.service;

import com.taskforge.dto.TaskRequest;
import com.taskforge.dto.TaskResponse;
import com.taskforge.entity.Project;
import com.taskforge.entity.Task;
import com.taskforge.entity.TaskStatus;
import com.taskforge.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository,
                       ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    public List<TaskResponse> getProjectTasks(Long projectId, String email) {

        Project project = projectService.getOwnedEntity(projectId, email);

        return taskRepository
                .findByProjectOrderByIdDesc(project)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse create(TaskRequest request, String email) {

        Project project =
                projectService.getOwnedEntity(request.projectId(), email);

        Task task = new Task();

        task.setTitle(request.title().trim());
        task.setDescription(request.description());

        task.setStatus(
                request.status() == null
                        ? TaskStatus.TODO
                        : request.status()
        );

        task.setDueDate(request.dueDate());
        task.setProject(project);

        return toResponse(taskRepository.save(task));
    }

    public TaskResponse updateStatus(
            Long taskId,
            TaskStatus status,
            String email) {

        for (var p : projectService.getMyProjects(email)) {

            Project project =
                    projectService.getOwnedEntity(p.id(), email);

            var optional =
                    taskRepository.findByIdAndProject(taskId, project);

            if (optional.isPresent()) {

                Task task = optional.get();

                task.setStatus(status);

                return toResponse(
                        taskRepository.save(task)
                );
            }
        }

        throw new IllegalArgumentException("Task not found");
    }

    public void delete(Long taskId, String email) {

        for (var p : projectService.getMyProjects(email)) {

            Project project =
                    projectService.getOwnedEntity(p.id(), email);

            var optional =
                    taskRepository.findByIdAndProject(taskId, project);

            if (optional.isPresent()) {

                taskRepository.delete(optional.get());

                return;
            }
        }

        throw new IllegalArgumentException("Task not found");
    }

    private TaskResponse toResponse(Task t) {

        return new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getDueDate(),
                t.getProject().getId(),
                t.getAssignee() == null
                        ? null
                        : t.getAssignee().getFullName()
        );
    }
}