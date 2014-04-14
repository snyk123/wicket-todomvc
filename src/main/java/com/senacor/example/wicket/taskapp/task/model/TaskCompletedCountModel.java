package com.senacor.example.wicket.taskapp.task.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Model chain to get the number of completed tasks.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public final class TaskCompletedCountModel extends AbstractReadOnlyModel<Integer> {
    private static final long serialVersionUID = 1L;

    private final IModel<List<Task>> completedTaskListModel;

    public TaskCompletedCountModel(IModel<List<Task>> completedTaskListModel) {
        this.completedTaskListModel = completedTaskListModel;
    }

    @Override
    public Integer getObject() {
        return completedTaskListModel.getObject().size();
    }

    @Override
    public void detach() {
        completedTaskListModel.detach();
        super.detach();
    }
}
