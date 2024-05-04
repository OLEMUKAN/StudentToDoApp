package com.example.studenttodoapp.model;

public class Task {
    private int id;
    private String name;
    private String dueDate;
    private String priority;
    private boolean isCompleted;

    public Task(int id, String name, String dueDate, String priority, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
