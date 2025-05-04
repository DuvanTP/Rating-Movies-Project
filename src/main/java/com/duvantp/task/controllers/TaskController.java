package com.duvantp.task.controllers;

import com.duvantp.task.models.Task;
import com.duvantp.task.repositories.TaskRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @CrossOrigin
    @GetMapping
    public List<Task> getAllTasks(
        @RequestParam(required = false) Integer priority,
        @RequestParam(required = false) Integer dueYear,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
    
        if (priority != null && dueYear != null) {
            return taskRepository.findByPriorityAndDueYear(priority, dueYear, sort);
        } else if (priority != null) {
            return taskRepository.findByPriority(priority, sort);
        } else if (dueYear != null) {
            return taskRepository.findByDueYear(dueYear, sort);
        } else {
            return taskRepository.findAll(sort);
        }
    }
    

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@Valid @PathVariable long id, @RequestBody Task updatedTask) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedTask.setId(id);
        Task savedTask = taskRepository.save(updatedTask);
        return ResponseEntity.ok(savedTask);
    }
}
