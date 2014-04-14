package com.senacor.example.wicket.taskapp.task.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator that compares two tasks based on their ID.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskIdComparator implements Comparator<Task>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Task o1, Task o2) {
        if (null == o1 && null == o2) {
            return 0;
        } else if (null == o1) {
            return -1;
        } else if (null == o2) {
            return 1;
        }
        return o1.getId().compareTo(o2.getId());
    }

}
