package com.github.imtiazea.taskmgr.repos;

import com.github.imtiazea.taskmgr.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    Task findByIdEquals(Long id);

    List<Task> findByStatus(String status);

    @Modifying
    @Query(value = "ALTER SEQUENCE tasks_id_seq RESTART WITH 1", nativeQuery = true)
    void resetId();
}
