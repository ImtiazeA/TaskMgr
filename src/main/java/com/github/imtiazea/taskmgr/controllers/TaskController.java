package com.github.imtiazea.taskmgr.controllers;

import com.github.imtiazea.taskmgr.dtos.TaskDTO;
import com.github.imtiazea.taskmgr.models.Task;
import com.github.imtiazea.taskmgr.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {

        Task savedTask = taskService.createTask(taskDTO);

        TaskDTO savedTaskDTO = modelMapper.map(savedTask, TaskDTO.class);

        return savedTaskDTO;
    }

    @PostMapping("/create")
    public TaskDTO createTask(@RequestHeader String taskDetails, @RequestHeader(required = false) Integer remindingInterval, @RequestHeader(required = false) String personName) {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskDetails(taskDetails);
        taskDTO.setRemindingInterval(remindingInterval != null ? remindingInterval : 300);
        taskDTO.setPersonName(personName);

        Task savedTask = taskService.createTask(taskDTO);

        TaskDTO savedTaskDTO = modelMapper.map(savedTask, TaskDTO.class);

        return savedTaskDTO;
    }

    @GetMapping("/get")
    public List<TaskDTO> getTasks() {

        List<Task> tasks = taskService.getTasks();
        List<TaskDTO> taskDTOS = tasks.stream().map(taskDTO -> modelMapper.map(taskDTO, TaskDTO.class))
                .collect(Collectors.toList());

        return taskDTOS;
    }


    @DeleteMapping("/delete")
    public String deleteTask(@RequestParam Long taskId) {

        taskService.archiveTask(taskId);

        return "Deleted Task: " + taskId;
    }

}
