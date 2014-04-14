package com.senacor.example.wicket.taskapp.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Sven Ludwig, Senacor Technologies AG
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskArray implements Serializable {
    private static final long serialVersionUID = 1L;

    private Task[] content;

    public Task[] getContent() {
        return content;
    }

    public void setContent(Task[] content) {
        this.content = content;
    }
}
