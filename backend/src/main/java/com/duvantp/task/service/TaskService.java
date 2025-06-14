package com.duvantp.task.service;

import com.duvantp.task.models.Task;
import com.duvantp.task.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(Integer priority, Integer dueYear, String sortBy, String direction) {
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

    public Optional<Task> getTaskById(long id) {
        return taskRepository.findById(id);
    }

    

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public boolean existsById(long id) {
        return taskRepository.existsById(id);
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> getUncompletedTasks() {
        return taskRepository.findByCompletedFalse();
    }
}
