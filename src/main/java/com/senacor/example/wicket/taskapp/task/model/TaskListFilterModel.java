package com.senacor.example.wicket.taskapp.task.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Model chain to deliver the list of tasks filtered with respect to a <code>TaskFilterEnum</code> filter setting.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskListFilterModel extends AbstractReadOnlyModel<List<Task>> {
    private static final long serialVersionUID = 1L;

    private final IModel<TaskListFilterSettingEnum> taskFilterSettingModel;

    private final IModel<List<Task>> taskListModel;

    public TaskListFilterModel(IModel<TaskListFilterSettingEnum> taskFilterSettingModel, IModel<List<Task>> taskListModel) {
        this.taskFilterSettingModel = taskFilterSettingModel;
        this.taskListModel = taskListModel;
    }

    @Override
    public List<Task> getObject() {
        TaskListFilterSettingEnum filterSetting = taskFilterSettingModel.getObject();
        List<Task> result;
        List<Task> taskList = taskListModel.getObject();

        if (TaskListFilterSettingEnum.OPEN == filterSetting) {
            result = new ArrayList<>(taskList.size() / 2);
            for (Task task : taskList) {
                if (Boolean.FALSE.equals(task.getCompleted())) {
                    result.add(task);
                }
            }
        } else if (TaskListFilterSettingEnum.COMPLETED == filterSetting) {
            result = new ArrayList<>(taskList.size() / 2);
            for (Task task : taskList) {
                if (Boolean.TRUE.equals(task.getCompleted())) {
                    result.add(task);
                }
            }
        } else {
            result = new ArrayList<>(taskList);
        }

        return result;
    }

    @Override
    public void detach() {
        taskFilterSettingModel.detach();
        taskListModel.detach();
        super.detach();
    }
}
