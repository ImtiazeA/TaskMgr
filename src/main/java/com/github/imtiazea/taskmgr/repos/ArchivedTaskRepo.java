package com.github.imtiazea.taskmgr.repos;

import com.github.imtiazea.taskmgr.models.ArchivedTask;
import com.github.imtiazea.taskmgr.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivedTaskRepo extends JpaRepository<ArchivedTask, Long> {
    Task findByIdEquals(Long id);

    List<Task> findByStatus(String status);
}
