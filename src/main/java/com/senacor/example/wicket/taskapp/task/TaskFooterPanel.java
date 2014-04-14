package com.senacor.example.wicket.taskapp.task;

import com.senacor.example.wicket.taskapp.task.model.Task;
import com.senacor.example.wicket.taskapp.task.model.TaskClearCompletedLabelModel;
import com.senacor.example.wicket.taskapp.task.model.TaskCompletedCountModel;
import com.senacor.example.wicket.taskapp.task.model.TaskDeletedEvent;
import com.senacor.example.wicket.taskapp.task.model.TaskCompletedListModel;
import com.senacor.example.wicket.taskapp.task.model.TaskListFilterSettingChangedEvent;
import com.senacor.example.wicket.taskapp.task.model.TaskListFilterSettingEnum;
import com.senacor.example.wicket.taskapp.task.model.TaskRemainingCountModel;
import com.senacor.example.wicket.taskapp.task.service.TaskCrudService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Footer.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskFooterPanel extends Panel {
    private static final long serialVersionUID = 1L;

    /**
     * Service for task management.
     * TODO use dependency injection
     */
    private TaskCrudService taskCrudService = new TaskCrudService();

    /**
     * Constructor.
     * @param id Component-ID.
     */
    public TaskFooterPanel(String id, IModel<List<Task>> taskListModel,
                           final IModel<TaskListFilterSettingEnum> taskListFilterSettingModel) {
        super(id, taskListModel);
        setOutputMarkupId(true);

        // counter for remaining tasks
        add(new Label("remainingCountLabel", new TaskRemainingCountModel((IModel<List<Task>>) getDefaultModel())));

        // links to change the filter setting
        add(new AjaxFallbackLink("taskFilterSettingAllLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                taskListFilterSettingModel.setObject(TaskListFilterSettingEnum.ALL);
                send(this, Broadcast.BUBBLE, new TaskListFilterSettingChangedEvent());
            }
        });

        add(new AjaxFallbackLink("taskFilterSettingOpenLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                taskListFilterSettingModel.setObject(TaskListFilterSettingEnum.OPEN);
                send(this, Broadcast.BUBBLE, new TaskListFilterSettingChangedEvent());
            }
        });

        add(new AjaxFallbackLink("taskFilterSettingCompletedLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                taskListFilterSettingModel.setObject(TaskListFilterSettingEnum.COMPLETED);
                send(this, Broadcast.BUBBLE, new TaskListFilterSettingChangedEvent());
            }
        });

        // button to remove all completed tasks
        AjaxButton button = new AjaxButton("clearCompletedButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                List<Task> tasks = (List<Task>) getForm().getModelObject();
                for (Task task : tasks) {
                    taskCrudService.deleteTask(task);
                    send(this, Broadcast.BUBBLE, new TaskDeletedEvent());
                }
            }

            @Override
            public void onEvent(IEvent<?> event) {
                if (event.getPayload() instanceof AjaxRequestTarget) {
                    ((AjaxRequestTarget) event.getPayload()).add(this);
                }
            }
        };
        button.setOutputMarkupId(true);

        // model chain to calculate the label of the button to remove all completed tasks
        IModel<String> labelModel = new TaskClearCompletedLabelModel(new TaskCompletedCountModel(
                new TaskCompletedListModel((IModel<List<Task>>) getDefaultModel())));
        button.add(new Label("clearCompletedButtonLabel", labelModel).setRenderBodyOnly(true));

        // form to support the button to remove all completed tasks
        Form<List<Task>> form = new Form<>("clearCompletedForm",
                new TaskCompletedListModel((IModel<List<Task>>) getDefaultModel()));
        form.add(button);
        add(form);
    }

    /**
     * Standard consumption of the default event raised by Wicket when an AJAX request is processed.
     * <p/>
     * In this way, the component will be re-rendered on any AJAX request.
     * @param event Some event.
     */
    public void onEvent(IEvent event) {
        if (event.getPayload() instanceof AjaxRequestTarget) {
            ((AjaxRequestTarget) event.getPayload()).add(this);
        }
    }
}
