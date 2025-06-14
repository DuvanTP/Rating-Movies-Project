package com.duvantp.task.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
//import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Min(0)
    @Max(10)
    private Integer progress = 0;
    @Column(nullable = false)
    private boolean completed = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    }

    