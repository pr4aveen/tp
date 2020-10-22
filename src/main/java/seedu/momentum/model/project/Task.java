package seedu.momentum.model.project;

import java.util.Set;

import seedu.momentum.commons.core.Date;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.model.timer.UniqueDurationList;

public class Task extends Project {

    /**
     * Constructs a {@code Task}.
     *
     * @param name A valid name.
     * @param description A description of the task.
     * @param createdDate A date associated with the creation of the task.
     * @param deadline A deadline associated with the task.
     * @param tags A set of tags associated to the task.
     * @param durations A list of {@code WorkDuration} associated with the task.
     * @param timer A timer associated with the task.
     */
    public Task(Name name, Description description, Date createdDate, Deadline deadline,
                Set<Tag> tags, UniqueDurationList durations, Timer timer) {
        super(name, description, createdDate, deadline, tags, durations, timer);
    }

    /**
     * Constructs a new {@code Task}
     *
     * @param name A valid name.
     * @param createdDate A date associated with the creation of the task
     * @param deadline A deadline associated with the task.
     * @param description A description of the task.
     * @param tags A set of tags associated to the task.
     */
    public Task(Name name, Description description, Date createdDate, Deadline deadline, Set<Tag> tags) {
        super(name, description, createdDate, deadline, tags);
    }

    /**
     * Returns true if the instance is a Task. Returns false otherwise.
     */
    @Override
    public boolean isTask() {
        return true;
    }
}
