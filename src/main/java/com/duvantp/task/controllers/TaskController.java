package com.duvantp.task.controllers;

import com.duvantp.task.models.Task;
import com.duvantp.task.service.TaskService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks(
        @RequestParam(required = false) Integer priority,
        @RequestParam(required = false) Integer dueYear,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return taskService.getAllTasks(priority, dueYear, sortBy, direction);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTasks() {
        return ResponseEntity.ok(taskService.getCompletedTasks());
    }

    @GetMapping("/completed=false")
    public ResponseEntity<List<Task>> getUncompletedTasks() {
        return ResponseEntity.ok(taskService.getUncompletedTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@Valid @PathVariable long id, @RequestBody Task updatedTask) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedTask.setId(id);
        Task savedTask = taskService.updateTask(updatedTask);
        return ResponseEntity.ok(savedTask);
    }
}
