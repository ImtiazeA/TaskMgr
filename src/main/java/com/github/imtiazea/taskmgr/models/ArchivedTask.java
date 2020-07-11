package com.github.imtiazea.taskmgr.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archived_tasks")
public class ArchivedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "archived_tasks_id_seq")
    @SequenceGenerator(name = "archived_tasks_id_seq", sequenceName = "archived_tasks_id_seq", allocationSize = 1)
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
    private LocalDateTime archivedAt;
}