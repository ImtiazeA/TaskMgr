package com.github.imtiazea.taskmgr.services;

import com.github.imtiazea.taskmgr.dtos.TaskDTO;
import com.github.imtiazea.taskmgr.models.Task;
import com.github.imtiazea.taskmgr.repos.TaskRepo;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskArchiverService {

    private TaskRepo taskRepo;
    private ModelMapper modelMapper;

    public TaskArchiverService(TaskRepo taskRepo, ModelMapper modelMapper) {
        this.taskRepo = taskRepo;
        this.modelMapper = modelMapper;
    }

    //24 Hours = 86,400,000 Milliseconds
    @Scheduled(fixedDelay = 86_400_000)
    @Transactional
    public void cleanUp() {

        List<Task> tasks = taskRepo.findAll();

        List<Task> previousTasks = tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .peek(task -> task.setId(null))
                .map(task -> modelMapper.map(task, Task.class))
                .collect(Collectors.toList());

        taskRepo.deleteAll();
        taskRepo.resetId();
        taskRepo.saveAll(previousTasks);
    }

}
