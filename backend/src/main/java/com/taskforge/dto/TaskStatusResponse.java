package com.taskforge.dto;

import com.taskforge.entity.Task;

public class TaskStatusResponse {

    private Task task;
    private boolean projectCompleted;

    public TaskStatusResponse(Task task, boolean projectCompleted) {
        this.task = task;
        this.projectCompleted = projectCompleted;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isProjectCompleted() {
        return projectCompleted;
    }

    public void setProjectCompleted(boolean projectCompleted) {
        this.projectCompleted = projectCompleted;
    }
}