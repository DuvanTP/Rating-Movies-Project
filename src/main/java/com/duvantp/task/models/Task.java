package com.duvantp.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tasks") 
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "title") 
    private String title;
    private String details;
    @Column(name = "due_year")
    private Integer dueYear;
    @Min(1)
    @Max(10)
    private Integer priority;
    @Min(1)
    @Max(10)
    private Integer progress;
    @Column(nullable = false)
    private boolean completed = false;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getDueYear() {
        return dueYear;
    }

    public void setDueYear(Integer dueYear) {
        this.dueYear = dueYear;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public boolean getcompleted() {
        return completed;
    }

    public void setcompleted(boolean completed) {
        this.completed = completed;
    }
}
