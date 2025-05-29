package com.duvantp.task.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.duvantp.task.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriority(Integer priority, Sort sort);
    List<Task> findByDueYear(Integer dueYear, Sort sort);
    List<Task> findByPriorityAndDueYear(Integer priority, Integer dueYear, Sort sort);
    List<Task> findByCompletedTrue();
    List<Task> findByCompletedFalse();
 }
