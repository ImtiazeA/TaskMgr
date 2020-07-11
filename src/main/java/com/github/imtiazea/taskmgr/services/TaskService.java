package com.github.imtiazea.taskmgr.services;

import com.github.imtiazea.taskmgr.dtos.TaskDTO;
import com.github.imtiazea.taskmgr.models.ArchivedTask;
import com.github.imtiazea.taskmgr.models.Task;
import com.github.imtiazea.taskmgr.repos.ArchivedTaskRepo;
import com.github.imtiazea.taskmgr.repos.TaskRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.github.imtiazea.taskmgr.enums.TaskStatus.ACTIVE;
import static com.github.imtiazea.taskmgr.enums.TaskStatus.ARCHIVED;

@Service
public class TaskService {

    private TaskRepo taskRepo;
    private ModelMapper modelMapper;
    private ArchivedTaskRepo archivedTaskRepo;

    public TaskService(TaskRepo taskRepo, ModelMapper modelMapper, ArchivedTaskRepo archivedTaskRepo) {
        this.taskRepo = taskRepo;
        this.modelMapper = modelMapper;
        this.archivedTaskRepo = archivedTaskRepo;
    }

    public Task createTask(TaskDTO taskDTO) {
        LocalDateTime now = LocalDateTime.now();
        int requestedRemindingInterval = taskDTO.getRemindingInterval();
        int remindingInterval = requestedRemindingInterval != 0 ? requestedRemindingInterval : 60;
        LocalDateTime nextRemindAt = now.plus(remindingInterval, ChronoUnit.SECONDS);

        Task task = new Task();
        task.setCreatedAt(now);
        task.setLastRemindedTime(now);
        task.setRemindingInterval(remindingInterval);
        task.setNextRemindAt(nextRemindAt);
        task.setTaskDetails(taskDTO.getTaskDetails());
        task.setPersonName(taskDTO.getPersonName());
        task.setStartAfter(taskDTO.getStartAfter());
        task.setStopAfter(taskDTO.getStopAfter());
        task.setStatus(ACTIVE.name());

        Task savedTask = taskRepo.save(task);
        return savedTask;
    }

    public void archiveTask(Long taskId) {
        Task task = taskRepo.findByIdEquals(taskId);

        if (task == null) {
            throw new RuntimeException("Task Not Found");
        }

        ArchivedTask archivedTask = modelMapper.map(task, ArchivedTask.class);
        archivedTask.setId(null);
        archivedTask.setStatus(ARCHIVED.name());
        archivedTask.setArchivedAt(LocalDateTime.now());

        taskRepo.delete(task);
        archivedTaskRepo.save(archivedTask);

    }

    public List<Task> getTasks() {
        List<Task> tasks = taskRepo.findByStatus(ACTIVE.name());
        return tasks;
    }
}
