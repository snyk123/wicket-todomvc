package com.senacor.example.wicket.taskapp.task.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * Model chain to get the label for the button to remove all completed tasks.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public final class TaskClearCompletedLabelModel extends AbstractReadOnlyModel<String> {
    private static final long serialVersionUID = 1L;

    private final IModel<Integer> completedTaskCountModel;

    public TaskClearCompletedLabelModel(IModel<Integer> completedTaskCountModel) {
        this.completedTaskCountModel = completedTaskCountModel;
    }

    @Override
    public String getObject() {
        return "Clear completed (" + completedTaskCountModel.getObject() + ")";
    }

    @Override
    public void detach() {
        completedTaskCountModel.detach();
        super.detach();
    }
}
