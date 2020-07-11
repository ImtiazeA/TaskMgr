package com.github.imtiazea.taskmgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskMgrApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskMgrApplication.class, args);
    }

}
