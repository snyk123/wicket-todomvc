package com.senacor.example.wicket.taskapp.task.model;

import com.senacor.example.wicket.taskapp.task.service.TaskListReadService;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

/**
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskListModel extends LoadableDetachableModel<List<Task>> {

    /**
     * Service for task management.
     * TODO use dependency injection
     */
    private TaskListReadService taskListReadService = new TaskListReadService();

    @Override
    public void setObject(List<Task> object) {
        throw new UnsupportedOperationException("The TaskListModel is read only.");
    }

    @Override
    protected List<Task> load() {
        return taskListReadService.readTaskList();
    }
}
