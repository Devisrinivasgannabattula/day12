package com.taskforge.service;

import com.taskforge.dto.ProjectRequest;
import com.taskforge.dto.ProjectResponse;
import com.taskforge.entity.Project;
import com.taskforge.entity.User;
import com.taskforge.repository.ProjectRepository;
import com.taskforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<ProjectResponse> getMyProjects(String email) {
        User user = getUser(email);
        return projectRepository.findByOwnerOrderByIdDesc(user).stream().map(this::toResponse).toList();
    }

    public ProjectResponse create(ProjectRequest request, String email) {
        User user = getUser(email);
        Project project = new Project();
        project.setName(request.name().trim());
        project.setDescription(request.description());
        project.setOwner(user);
        return toResponse(projectRepository.save(project));
    }

    public void delete(Long id, String email) {
        User user = getUser(email);
        Project project = projectRepository.findByIdAndOwner(id, user)
            .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        projectRepository.delete(project);
    }

    public Project getOwnedEntity(Long id, String email) {
        return projectRepository.findByIdAndOwner(id, getUser(email))
            .orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    private User getUser(String email) {
        return userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private ProjectResponse toResponse(Project p) {
        return new ProjectResponse(
            p.getId(), p.getName(), p.getDescription(),
            p.getOwner().getId(), p.getOwner().getFullName()
        );
    }
}

//package com.taskforge.service;
//
//import com.taskforge.dto.CompletedProjectResponse;
//import com.taskforge.dto.ProjectRequest;
//import com.taskforge.dto.ProjectResponse;
//import com.taskforge.entity.Project;
//import com.taskforge.entity.Task;
//import com.taskforge.entity.TaskStatus;
//import com.taskforge.entity.User;
//import com.taskforge.repository.ProjectRepository;
//import com.taskforge.repository.TaskRepository;
//import com.taskforge.repository.UserRepository;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProjectService {
//
//    private final ProjectRepository projectRepository;
//    private final UserRepository userRepository;
//    private final TaskRepository taskRepository;
//
//    public ProjectService(
//            ProjectRepository projectRepository,
//            UserRepository userRepository,
//            TaskRepository taskRepository
//    ) {
//        this.projectRepository = projectRepository;
//        this.userRepository = userRepository;
//        this.taskRepository = taskRepository;
//    }
//
//    public List<ProjectResponse> getMyProjects(String email) {
//
//        User user = getUser(email);
//
//        return projectRepository
//                .findByOwnerOrderByIdDesc(user)
//                .stream()
//                .map(this::toResponse)
//                .toList();
//    }
//
//    public ProjectResponse create(
//            ProjectRequest request,
//            String email
//    ) {
//
//        User user = getUser(email);
//
//        Project project = new Project();
//
//        project.setName(request.name().trim());
//        project.setDescription(request.description());
//        project.setOwner(user);
//
//        return toResponse(
//                projectRepository.save(project)
//        );
//    }
//
//    public void delete(
//            Long id,
//            String email
//    ) {
//
//        User user = getUser(email);
//
//        Project project =
//                projectRepository
//                        .findByIdAndOwner(id, user)
//                        .orElseThrow(() ->
//                                new IllegalArgumentException(
//                                        "Project not found"
//                                )
//                        );
//
//        projectRepository.delete(project);
//    }
//
//    public Project getOwnedEntity(
//            Long id,
//            String email
//    ) {
//
//        return projectRepository
//                .findByIdAndOwner(
//                        id,
//                        getUser(email)
//                )
//                .orElseThrow(() ->
//                        new IllegalArgumentException(
//                                "Project not found"
//                        )
//                );
//    }
//
//    // =========================================================
//    // COMPLETED PROJECTS
//    // =========================================================
//
//    public List<CompletedProjectResponse> getCompletedProjects(
//            String email
//    ) {
//
//        User user = getUser(email);
//
//        List<Project> projects =
//                projectRepository.findCompletedProjects(
//                        user,
//                        TaskStatus.DONE
//                );
//
//        return projects.stream()
//                .map(project -> {
//
//                    List<Task> tasks =
//                            taskRepository
//                                    .findByProjectOrderByIdDesc(
//                                            project
//                                    );
//
//                    return new CompletedProjectResponse(
//                            project,
//                            tasks
//                    );
//                })
//                .toList();
//    }
//
//    // =========================================================
//    // USER
//    // =========================================================
//
//    private User getUser(String email) {
//
//        return userRepository
//                .findByEmailIgnoreCase(email)
//                .orElseThrow(() ->
//                        new IllegalArgumentException(
//                                "User not found"
//                        )
//                );
//    }
//
//    // =========================================================
//    // RESPONSE
//    // =========================================================
//
//    private ProjectResponse toResponse(Project p) {
//
//        return new ProjectResponse(
//                p.getId(),
//                p.getName(),
//                p.getDescription(),
//                p.getOwner().getId(),
//                p.getOwner().getFullName()
//        );
//    }
//}
