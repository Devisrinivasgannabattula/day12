package com.taskforge.dto;

import com.taskforge.entity.Project;
import com.taskforge.entity.Task;

import java.util.List;

public class CompletedProjectResponse {

    private Project project;
    private List<Task> tasks;

    public CompletedProjectResponse(Project project, List<Task> tasks) {
        this.project = project;
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}