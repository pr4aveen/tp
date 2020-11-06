//@@author pr4aveen
package seedu.momentum.model.project;

import java.util.Set;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.UniqueItem;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.TimerWrapper;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Task in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task extends TrackedItem implements UniqueItem<TrackedItem> {

    /**
     * Constructs a {@code Task}.
     *
     * @param name               A valid name.
     * @param description        A description of the task.
     * @param completionStatus   A completion status of the task.
     * @param createdDateWrapper A date associated with the creation of the task.
     * @param deadline           A deadline associated with the task.
     * @param reminder           A reminder associated with the tracked item.
     * @param tags               A set of tags associated to the task.
     * @param durations          A list of durations spent working on the task.
     * @param timerWrapper       A timerWrapper associated with the task.
     */
    public Task(Name name, Description description, CompletionStatus completionStatus, DateWrapper createdDateWrapper,
                Deadline deadline, Reminder reminder, Set<Tag> tags, UniqueItemList<WorkDuration> durations,
                TimerWrapper timerWrapper) {
        super(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                timerWrapper);
    }

    /**
     * Constructs a new {@code Task}.
     *
     * @param name               A valid name.
     * @param completionStatus   A completion status of the task.
     * @param createdDateWrapper A date associated with the creation of the task.
     * @param deadline           A deadline associated with the task.
     * @param reminder           A reminder associated with the tracked item.
     * @param description        A description of the task.
     * @param tags               A set of tags associated to the task.
     */
    public Task(Name name, Description description, CompletionStatus completionStatus, DateWrapper createdDateWrapper,
                Deadline deadline, Reminder reminder, Set<Tag> tags) {
        super(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags);
    }

    //@@author boundtotheearth
    /**
     * Returns a copy of this task with its timerWrapper started.
     *
     * @return A copy of this task, but with its timerWrapper started.
     */
    @Override
    public Task startTimer() {
        LOGGER.info("Started Timer For: " + name.fullName);
        TimerWrapper newTimerWrapper = timerWrapper.start();
        return new Task(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                newTimerWrapper);
    }

    /**
     * Returns a copy of this task with its timerWrapper stopped, then adds the timed duration into
     * the list.
     *
     * @return A copy of this task, but with its timerWrapper stopped.
     */
    @Override
    public Task stopTimer() {
        LOGGER.info("Stopped Timer For: " + name.fullName);
        TimerWrapper newTimerWrapper = timerWrapper.stop();
        WorkDuration duration = new WorkDuration(newTimerWrapper.getStartTime(), newTimerWrapper.getStopTime());
        UniqueItemList<WorkDuration> newDurations = durations.copy();
        newDurations.add(duration);
        return new Task(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags,
                newDurations, newTimerWrapper);
    }

    //@@author claracheong4
    @Override
    public Task removeReminder() {
        Reminder newReminder = reminder.remove();
        return new Task(name, description, completionStatus, createdDateWrapper, deadline, newReminder, tags, durations,
                timerWrapper);
    }

    @Override
    public Task updateExpiredReminder() {
        Reminder newReminder = reminder.updateExpired();
        return new Task(name, description, completionStatus, createdDateWrapper, deadline, newReminder, tags, durations,
                timerWrapper);
    }

    //@@author pr4aveen
    /**
     * Checks whether two tracked item are the same.
     * This defines a weaker notion of equality between two tasks.
     *
     * @param otherTrackedItem The other tracked item to check against.
     * @return True if both tracked item of the same name have at least one other identity field that is the same.
     */
    @Override
    public boolean isSameAs(TrackedItem otherTrackedItem) {
        if (!(otherTrackedItem instanceof Task)) {
            return false;
        }

        return super.isSameAs(otherTrackedItem);
    }

    /**
     * Checks whether the instance is a task.
     *
     * @return True if the instance is a Task, false otherwise.
     */
    @Override
    public boolean isTask() {
        return true;
    }

    /**
     * Checks whether another object is equal to this tracked item.
     * This defines a stronger notion of equality between two tracked items.
     *
     * @param other Other object to check.
     * @return True if both tracked items have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Task)) {
            return false;
        }
        return super.equals(other);
    }
}
