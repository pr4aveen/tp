//@@author pr4aveen
package seedu.momentum.model.project;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.UniqueItem;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.TimerWrapper;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Tracked Item in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class TrackedItem implements UniqueItem<TrackedItem> {
    protected static final Logger LOGGER = LogsCenter.getLogger(TrackedItem.class);

    // Identity fields
    protected final Name name;

    // data fields
    protected final Description description;
    protected final CompletionStatus completionStatus;
    protected final DateWrapper createdDateWrapper;
    protected final Deadline deadline;
    protected final Reminder reminder;
    protected final Set<Tag> tags = new HashSet<>();
    protected final TimerWrapper timerWrapper;
    protected final UniqueItemList<WorkDuration> durations;

    /**
     * Constructs a {@code TrackedItem}.
     *
     * @param name               A valid name.
     * @param description        A description of the tracked item.
     * @param completionStatus   A completion status of the tracked item.
     * @param createdDateWrapper A dateWrapper associated with the creation of the tracked item.
     * @param deadline           A deadline associated with the tracked item.
     * @param reminder           A reminder associated with the tracked item.
     * @param tags               A set of tags associated to the tracked item.
     * @param durations          A list of durations spent working on the tracked item.
     * @param timerWrapper       A timerWrapper associated with the tracked item.
     */
    public TrackedItem(Name name, Description description, CompletionStatus completionStatus,
                       DateWrapper createdDateWrapper, Deadline deadline, Reminder reminder,
                       Set<Tag> tags, UniqueItemList<WorkDuration> durations, TimerWrapper timerWrapper) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.createdDateWrapper = createdDateWrapper;
        this.deadline = deadline;
        this.reminder = reminder;
        this.tags.addAll(tags);
        this.durations = durations;
        this.timerWrapper = timerWrapper;
    }

    /**
     * Constructs a new {@code TrackedItem}.
     *
     * @param name               A valid name.
     * @param description        A description of the tracked item.
     * @param completionStatus   A completion status of the tracked item.
     * @param createdDateWrapper A dateWrapper associated with the creation of the tracked item.
     * @param deadline           A deadline associated with the tracked item.
     * @param reminder           A reminder associated with the tracked item.
     * @param tags               A set of tags associated to the tracked item.
     */
    public TrackedItem(Name name, Description description, CompletionStatus completionStatus,
                       DateWrapper createdDateWrapper, Deadline deadline, Reminder reminder, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.createdDateWrapper = createdDateWrapper;
        this.deadline = deadline;
        this.reminder = reminder;
        this.tags.addAll(tags);
        this.durations = new UniqueItemList<>();
        this.timerWrapper = new TimerWrapper();
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public DateWrapper getCreatedDate() {
        return createdDateWrapper;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Reminder getReminder() {
        return reminder;
    }

    //@@author kkangs0226
    /**
     * Gets Deadline and name for {@code DeadLineCompare} comparator.
     *
     * @return Null if deadline is empty as {@code Comparator.nullsLast} method is used.
     */
    public HashMap<String, Object> getNullOrDeadline() {
        if (deadline.isEmpty()) {
            return null;
        }
        Name name = getName();
        Deadline deadline = getDeadline();
        HashMap<String, Object> nameDeadlineMap = new HashMap<>();
        nameDeadlineMap.put("name", name);
        nameDeadlineMap.put("deadline", deadline);

        return nameDeadlineMap;
    }

    //@@author pr4aveen
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return The tag set associated with this tracked item.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    //@@author boundtotheearth
    /**
     * Returns an unmodifiable view of the durations list.
     *
     * @return The list of durations spent working on this tracked item.
     */
    public ObservableList<WorkDuration> getDurationList() {
        return durations.asUnmodifiableObservableList();
    }

    //@@author claracheong4
    /**
     * Remove the reminder of a trackedItem.
     *
     * @return A new copy of the tracked item with the reminder removed.
     */
    public abstract TrackedItem removeReminder();

    /**
     * Updates the expiry of the reminder.
     *
     * @return A new copy of the tracked item with the reminder updated.
     */
    public abstract TrackedItem updateExpiredReminder();

    //@@author boundtotheearth
    /**
     * Returns a copy of this tracked item with its timerWrapper started.
     *
     * @return A copy of this tracked item, but with its timerWrapper started.
     */
    public abstract TrackedItem startTimer();

    /**
     * Returns a copy of this tracked item with its timerWrapper stopped, then adds the timed duration into
     * the list.
     *
     * @return A copy of this tracked item, but with its timerWrapper stopped.
     */
    public abstract TrackedItem stopTimer();

    public TimerWrapper getTimer() {
        return timerWrapper;
    }

    /**
     * Checks if the tracked item's timerWrapper is currently running.
     *
     * @return True is the tracked item's timerWrapping is running, false otherwise.
     */
    public boolean isRunning() {
        return timerWrapper.isRunning();
    }

    //@@author pr4aveen
    /**
     * Checks if two tracked items are the same.
     * This defines a weaker notion of equality between two projects.
     *
     * @return True if both tracked item of the same name have at least one other identity field that is the same,
     * false otherwise.
     */
    public boolean isSameAs(TrackedItem otherTrackedItem) {
        if (otherTrackedItem == this) {
            return true;
        }

        return otherTrackedItem != null
                && otherTrackedItem.getName().equals(getName())
                && otherTrackedItem.getDescription().equals(getDescription())
                && otherTrackedItem.getCompletionStatus().equals(getCompletionStatus())
                && otherTrackedItem.getDeadline().equals(getDeadline())
                && otherTrackedItem.getReminder().equals(getReminder());
    }

    /**
     * Checks whether the instance is a task.
     *
     * @return True if the instance is a Task, false otherwise.
     */
    public abstract boolean isTask();

    /**
     * Checks if an object is the same as the instance of the tracked item.
     * This defines a stronger notion of equality between two tracked items.
     *
     * @param other Object to check against.
     * @return True if the object is the same as the instance of the tracked item, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TrackedItem)) {
            return false;
        }

        TrackedItem otherTrackedItem = (TrackedItem) other;
        return otherTrackedItem.getName().equals(getName())
                && otherTrackedItem.getDescription().equals(getDescription())
                && otherTrackedItem.getCompletionStatus().equals(getCompletionStatus())
                && otherTrackedItem.getDeadline().equals(getDeadline())
                && otherTrackedItem.getReminder().equals(getReminder())
                && otherTrackedItem.getTags().equals(getTags())
                && otherTrackedItem.getDurationList().equals(getDurationList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags,
                durations, timerWrapper);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Completion Status: ")
                .append(getCompletionStatus())
                .append(" Created Date: ")
                .append(getCreatedDate())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Reminder: ")
                .append(getReminder().getFormattedReminder())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
