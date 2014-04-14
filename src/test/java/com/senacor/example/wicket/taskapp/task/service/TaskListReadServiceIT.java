package com.senacor.example.wicket.taskapp.task.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskListReadServiceIT {

    private static final Logger log = LoggerFactory.getLogger(TaskCrudServiceIT.class);

    @Test
    public void shouldRetrieveTaskList() {
        new TaskListReadService().readTaskList();
    }

}
