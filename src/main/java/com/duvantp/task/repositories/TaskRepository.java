package com.duvantp.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duvantp.task.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> { }
