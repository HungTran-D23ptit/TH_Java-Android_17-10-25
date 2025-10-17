package com.example.s_task;

import java.io.Serializable;

public class TaskModel implements Serializable {
    private String title;
    private String description;
    private long dueTime;

    public TaskModel(String title, String description, long dueTime) {
        this.title = title;
        this.description = description;
        this.dueTime = dueTime;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public long getDueTime() { return dueTime; }
}
