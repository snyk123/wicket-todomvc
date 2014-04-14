package com.senacor.example.wicket.taskapp.task.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Model chain to get the list of the completed tasks.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public final class TaskCompletedListModel extends AbstractReadOnlyModel<List<Task>> {
    private static final long serialVersionUID = 1L;

    private final IModel<List<Task>> taskListModel;

    public TaskCompletedListModel(IModel<List<Task>> taskListModel) {
        this.taskListModel = taskListModel;
    }

    @Override
    public List<Task> getObject() {
        List<Task> taskList = taskListModel.getObject();
        List<Task> completedTaskList = new ArrayList<>(taskList.size() / 2);
        for (Task task : taskList) {
            if (Boolean.TRUE.equals(task.getCompleted())) {
                completedTaskList.add(task);
            }
        }
        return completedTaskList;
    }

    @Override
    public void detach() {
        taskListModel.detach();
        super.detach();
    }
}
