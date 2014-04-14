package com.senacor.example.wicket.taskapp.task;

import com.senacor.example.wicket.taskapp.task.model.Task;
import com.senacor.example.wicket.taskapp.task.model.TaskDeletedEvent;
import com.senacor.example.wicket.taskapp.task.service.TaskCrudService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

/**
 * Listing of tasks.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskListPanel extends Panel {
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
    public TaskListPanel(String id, IModel<List<Task>> taskListModel) {
        super(id, taskListModel);

        add(new ListView<Task>("taskList", (IModel<List<Task>>) getDefaultModel()) {
            @Override
            protected void populateItem(final ListItem<Task> item) {
                AjaxCheckBox checkBox = new AjaxCheckBox("completedCheckbox",
                        new PropertyModel<Boolean>(item.getModel(), "completed")) {
                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        Task task = item.getModelObject();
                        Boolean value = getModelObject();
                        task.setCompleted(value);
                        taskCrudService.updateTask(task);
                    }
                };
                item.add(checkBox);

                item.add(new Label("titleLabel", new PropertyModel(item.getModel(), "title")));

                Form<Task> deleteTaskForm = new Form<>("deleteTaskForm", (IModel<Task>) item.getDefaultModel());
                deleteTaskForm.add(new AjaxButton("deleteButton", deleteTaskForm) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        taskCrudService.deleteTask((Task) getForm().getModelObject());
                        send(this, Broadcast.BUBBLE, new TaskDeletedEvent());
                    }
                });
                item.add(deleteTaskForm);
            }
        }.setReuseItems(false));
    }

}
