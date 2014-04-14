package com.senacor.example.wicket.taskapp.task.service;

import com.senacor.example.wicket.taskapp.task.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskCrudServiceIT {

    private static final Logger log = LoggerFactory.getLogger(TaskCrudServiceIT.class);

    @Test
    public void shouldCreateReadUpdateDeleteTask() {
        TaskCrudService taskCrudService = new TaskCrudService();

        Task freshTask = new Task();
        freshTask.setTitle("A temporary task from integration test.");
        Long id = taskCrudService.createTask(freshTask);

        Task existingTask = new Task();
        existingTask.setId(id);

        Task existingTaskRead = taskCrudService.readTask(existingTask);

        existingTaskRead.setTitle("An updated temporary task from integration test.");
        existingTaskRead.setCompleted(true);
        taskCrudService.updateTask(existingTaskRead);

        taskCrudService.deleteTask(existingTaskRead);

        List<Task> taskList = new TaskListReadService().readTaskList();
        log.info("Current task list: " + taskList);
        for (Task task : taskList) {
            assertFalse(StringUtils.contains(task.getTitle(), "temporary task from integration test."));
        }
    }

}
