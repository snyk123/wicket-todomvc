package com.senacor.example.wicket.taskapp.task;

import com.senacor.example.wicket.taskapp.task.model.Task;
import com.senacor.example.wicket.taskapp.task.service.TaskCrudService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Header.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskHeaderPanel extends Panel {
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
    public TaskHeaderPanel(String id) {
        super(id);

        // form with compound property model on a new task instance
        Form<Task> form = new Form<Task>("createTaskForm", new CompoundPropertyModel<>(new Task())) {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                // get the model object (after data binding including input validation and conversion)
                Task task = getModelObject();
                // create the task
                Task newTask = new Task();
                newTask.setCompleted(false);
                newTask.setTitle(task.getTitle());
                Long id = taskCrudService.createTask(newTask);
                newTask.setId(id);
                // provide new model object and clear the form input
                setModel(new CompoundPropertyModel<>(new Task()));
                clearInput();
                // let the header panel be re-rendered even in case of an AJAX request
                AjaxRequestTarget ajaxRequestTarget = getRequestCycle().find(AjaxRequestTarget.class);
                if (null != ajaxRequestTarget) {
                    ajaxRequestTarget.add(TaskHeaderPanel.this);
                }
            }
        };

        // input field for the title with Component-ID matching the property name in the model class
        form.add(new TextField("title"));

        add(form);

    }

}
