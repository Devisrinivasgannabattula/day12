package com.taskforge.repository;

import com.taskforge.entity.Project;
import com.taskforge.entity.Task;
import com.taskforge.entity.TaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectOrderByIdDesc(Project project);

    Optional<Task> findByIdAndProject(Long id, Project project);

    long countByProject(Project project);

    long countByProjectAndStatus(
            Project project,
            TaskStatus status
    );
}
