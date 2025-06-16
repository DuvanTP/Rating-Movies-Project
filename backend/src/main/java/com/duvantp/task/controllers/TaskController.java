package com.duvantp.task.controllers;

import com.duvantp.task.models.Task;
import com.duvantp.task.models.User;
import com.duvantp.task.repositories.TaskRepository;
import com.duvantp.task.repositories.UserRepository;
import com.duvantp.task.service.TaskService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskService taskService;

    // Crear tarea asociada a un usuario
    @PostMapping("/with-user")
    public Task createTask(@RequestBody Task task, @RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        task.setUser(user);
        return taskRepo.save(task);
    }

    // Obtener tareas por usuario
    @GetMapping("/by-user")
    public List<Task> getTasksByUser(@RequestParam Long userId) {
        return taskRepo.findByUserId(userId);
    }

    // Obtener todas las tareas con filtros
    @GetMapping("/all")
    public List<Task> getAllTasks(
        @RequestParam(required = false) Integer priority,
        @RequestParam(required = false) Integer dueYear,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return taskService.getAllTasks(priority, dueYear, sortBy, direction);
    }

    // Tareas completadas
    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTasks() {
        return ResponseEntity.ok(taskService.getCompletedTasks());
    }

    // Tareas no completadas
    @GetMapping("/uncompleted")
    public ResponseEntity<List<Task>> getUncompletedTasks() {
        return ResponseEntity.ok(taskService.getUncompletedTasks());
    }

    // Obtener tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar tarea
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@Valid @PathVariable long id, @RequestBody Task updatedTask) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User user = userRepo.findById(52L).orElseThrow();
        updatedTask.setId(id);
        updatedTask.setUser(user);
        Task savedTask = taskService.updateTask(updatedTask);
        return ResponseEntity.ok(savedTask);
    }
}
