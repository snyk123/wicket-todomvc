package com.senacor.example.wicket.taskapp;

import com.senacor.example.wicket.taskapp.task.TaskFooterPanel;
import com.senacor.example.wicket.taskapp.task.TaskHeaderPanel;
import com.senacor.example.wicket.taskapp.task.TaskListPanel;
import com.senacor.example.wicket.taskapp.task.model.Task;
import com.senacor.example.wicket.taskapp.task.model.TaskDeletedEvent;
import com.senacor.example.wicket.taskapp.task.model.TaskListFilterModel;
import com.senacor.example.wicket.taskapp.task.model.TaskListFilterSettingChangedEvent;
import com.senacor.example.wicket.taskapp.task.model.TaskListFilterSettingEnum;
import com.senacor.example.wicket.taskapp.task.model.TaskListModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * Page for task overview and management.
 */
public class TaskPage extends WebPage {
    private static final long serialVersionUID = 1L;

    /**
     * Model for the filter setting regarding the list of tasks.
     */
    private IModel<TaskListFilterSettingEnum> taskListFilterSettingModel = Model.of(TaskListFilterSettingEnum.ALL);

    /**
     * Model with the list of tasks.
     */
    private IModel<List<Task>> taskListModel = new TaskListFilterModel(taskListFilterSettingModel, new TaskListModel());

    /**
     * Constructor.
     */
    public TaskPage() {
        super();

        add(new TaskHeaderPanel("headerPanel").setOutputMarkupId(true));

        add(new TaskListPanel("contentPanel", taskListModel).setOutputMarkupId(true));

        add(new TaskFooterPanel("footerPanel", taskListModel, taskListFilterSettingModel).setOutputMarkupId(true));

    }

    /**
     * On certain functional events let components be re-rendered.
     * @param event Some event.
     */
    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);
        if (event.getPayload() instanceof TaskListFilterSettingChangedEvent) {
            AjaxRequestTarget ajaxRequestTarget = getRequestCycle().find(AjaxRequestTarget.class);
            if (null != ajaxRequestTarget) {
                ajaxRequestTarget.add(get("contentPanel"));
            }
        } else if (event.getPayload() instanceof TaskDeletedEvent) {
            AjaxRequestTarget ajaxRequestTarget = getRequestCycle().find(AjaxRequestTarget.class);
            if (null != ajaxRequestTarget) {
                ajaxRequestTarget.add(this);
            }
        }
    }
}
