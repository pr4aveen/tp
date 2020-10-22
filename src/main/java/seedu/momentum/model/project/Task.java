package seedu.momentum.model.project;

import java.util.Set;

import seedu.momentum.commons.core.Date;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Task in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task extends TrackedItem {

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
     * Returns a copy of this task with its timer started.
     *
     * @return A copy of this task, but with its timer started
     */
    @Override
    public Task startTimer() {
        Timer newTimer = timer.start();
        return new Task(name, description, createdDate, deadline, tags, durations, newTimer);
    }

    /**
     * Returns a copy of this task with its timer stopped, then adds the timed duration into
     * the list.
     *
     * @return A copy of this task, but with its timer stopped
     */
    @Override
    public Task stopTimer() {
        Timer newTimer = timer.stop();
        WorkDuration duration = new WorkDuration(newTimer.getStartTime(), newTimer.getStopTime());
        UniqueDurationList newDurations = new UniqueDurationList();
        newDurations.setDurations(durations);
        newDurations.add(duration);
        return new Task(name, description, createdDate, deadline, tags, newDurations, newTimer);
    }

    /**
     * Returns true if both tracked item of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two tasks.
     */
    @Override
    public boolean isSameTrackedItem(TrackedItem otherTrackedItem) {
        if (!(otherTrackedItem instanceof Task)) {
            return false;
        }

        return super.isSameTrackedItem(otherTrackedItem);
    }

    /**
     * Returns true if the instance is a Task. Returns false otherwise.
     */
    @Override
    public boolean isTask() {
        return true;
    }

    /**
     * Returns true if both tracked items have the same identity and data fields.
     * This defines a stronger notion of equality between two tracked items.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Task)) {
            return false;
        }

        return super.equals(other);
    }
}
