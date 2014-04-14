package com.senacor.example.wicket.taskapp.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Model object representing one task.
 * @author Sven Ludwig, Senacor Technologies AG
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    private Long id;

    /**
     * Version.
     */
    private String version;

    /**
     * Title.
     */
    private String title;

    /**
     * Has this task been completed?
     */
    private Boolean completed;

    /**
     * Default constructor.
     */
    public Task() {
    }

    /**
     * Full constructor.
     * @param id ID.
     * @param version Version.
     * @param title Title.
     * @param completed Has this task been completed?
     */
    public Task(Long id, String version, String title, Boolean completed) {
        this.id = id;
        this.version = version;
        this.title = title;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return !(id != null ? !id.equals(task.id) : task.id != null)
                && !(version != null ? !version.equals(task.version) : task.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
