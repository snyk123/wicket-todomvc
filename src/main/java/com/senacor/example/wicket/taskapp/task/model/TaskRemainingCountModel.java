package com.senacor.example.wicket.taskapp.task.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Model chain to get the number of remaining tasks.
 * <p/>
 * This is a model chain that begins at the model which delivers the current task list
 * and that ends with a model that performs the counting.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public final class TaskRemainingCountModel extends AbstractReadOnlyModel<Integer> {
    private static final long serialVersionUID = 1L;

    private IModel<List<Task>> taskListModel;

    public TaskRemainingCountModel(IModel<List<Task>> taskListModel) {
        this.taskListModel = taskListModel;
    }

    @Override
    public Integer getObject() {
        List<Task> taskList = taskListModel.getObject();
        int count = 0;
        for (Task task : taskList) {
            if (Boolean.FALSE.equals(task.getCompleted())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void detach() {
        taskListModel.detach();
        super.detach();
    }
}
