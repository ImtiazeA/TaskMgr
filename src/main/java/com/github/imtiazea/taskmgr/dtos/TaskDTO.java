package com.github.imtiazea.taskmgr.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private LocalDateTime createdAt;
    private int remindingInterval;
    private LocalDateTime nextRemindAt;
    private String taskDetails;
    private String personName;
    private String status;
    private LocalDateTime lastRemindedTime;
    private int remindCount;
    private LocalTime startAfter;
    private LocalTime stopAfter;
}
