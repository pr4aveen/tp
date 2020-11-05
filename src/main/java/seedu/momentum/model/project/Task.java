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
     * @param durations          A list of {@code WorkDuration} associated with the task.
     * @param timerWrapper       A timerWrapper associated with the task.
     */
    public Task(Name name, Description description, CompletionStatus completionStatus, DateWrapper createdDateWrapper,
                Deadline deadline, Reminder reminder, Set<Tag> tags, UniqueItemList<WorkDuration> durations,
                TimerWrapper timerWrapper) {
        super(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                timerWrapper);
    }

    /**
     * Constructs a new {@code Task}
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

    /**
     * Returns a copy of this task with its timerWrapper started.
     *
     * @return A copy of this task, but with its timerWrapper started.
     */
    @Override
    public Task startTimer() {
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
        TimerWrapper newTimerWrapper = timerWrapper.stop();
        WorkDuration duration = new WorkDuration(newTimerWrapper.getStartTime(), newTimerWrapper.getStopTime());
        UniqueItemList<WorkDuration> newDurations = durations.copy();
        newDurations.add(duration);
        return new Task(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags,
                newDurations, newTimerWrapper);
    }

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

    /**
     * Returns true if both tracked item of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two tasks.
     */
    @Override
    public boolean isSameAs(TrackedItem otherTrackedItem) {
        if (!(otherTrackedItem instanceof Task)) {
            return false;
        }

        return super.isSameAs(otherTrackedItem);
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
