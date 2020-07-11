package com.github.imtiazea.taskmgr.services;

import com.github.imtiazea.SimpleNotifier;
import com.github.imtiazea.taskmgr.enums.TaskStatus;
import com.github.imtiazea.taskmgr.models.Task;
import com.github.imtiazea.taskmgr.repos.TaskRepo;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TaskReminderService {

    private TaskRepo taskRepo;
    private SimpleNotifier simpleNotifier;
    private ModelMapper modelMapper;

    public TaskReminderService(TaskRepo taskRepo, SimpleNotifier simpleNotifier, ModelMapper modelMapper) {
        this.taskRepo = taskRepo;
        this.simpleNotifier = simpleNotifier;
        this.modelMapper = modelMapper;
    }

    @Scheduled(initialDelay = 30_000, fixedDelay = 30_000)
    public void remindAboutTask() {

        List<Task> activeTasks = taskRepo.findByStatus(TaskStatus.ACTIVE.name());

        Optional<Task> firstTask = activeTasks.stream()
                .sorted(Comparator.comparing(Task::getLastRemindedTime))
                .filter(this::isWithinTimeRange)
                .filter(task -> task.getNextRemindAt().isBefore(LocalDateTime.now()))
                .findFirst();

        // if no task to remind, just skip the process
        if (firstTask.isEmpty()) {
            return;
        }

        Task task = firstTask.get();
        LocalDateTime remindAt = task.getNextRemindAt();

        // the remind time did not pass, don't remind about it
        if (remindAt.isAfter(LocalDateTime.now())) {
            return;
        }

        // now remind me!
        Long taskId = task.getId();
        String taskDetails = task.getTaskDetails();
        int remindCount = task.getRemindCount() + 1;

        LocalDateTime now = LocalDateTime.now();
        int remindingInterval = task.getRemindingInterval();
        LocalDateTime nextRemindAt = now.plus(remindingInterval, ChronoUnit.SECONDS);

        task.setRemindCount(remindCount);
        task.setLastRemindedTime(LocalDateTime.now());
        task.setNextRemindAt(nextRemindAt);

        taskRepo.save(task);

        simpleNotifier.pushNotification(taskId + " - " + taskDetails + " (" + remindCount + ")", "", TrayIcon.MessageType.INFO);

    }

    private boolean isWithinTimeRange(Task task) {
        LocalTime startAfter = task.getStartAfter();
        LocalTime stopAfter = task.getStopAfter();

        if (startAfter == null || stopAfter == null) {
            return true;
        }

        return LocalTime.now().isBefore(stopAfter)
                &&
                LocalTime.now().isAfter(startAfter);
    }
}
