package seedu.momentum.model.project;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.Date;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Tracked Item in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class TrackedItem {

    // Identity fields
    protected final Name name;

    // data fields
    protected final Description description;
    protected final CompletionStatus completionStatus;
    protected final Date createdDate;
    protected final Deadline deadline;
    protected final Set<Tag> tags = new HashSet<>();
    protected final Timer timer;
    protected final UniqueDurationList durations;

    /**
     * Constructs a {@code TrackedItem}.
     *
     * @param name             A valid name.
     * @param description      A description of the tracked item.
     * @param completionStatus A completion status of the tracked item.
     * @param createdDate      A date associated with the creation of the tracked item.
     * @param deadline         A deadline associated with the tracked item.
     * @param tags             A set of tags associated to the tracked item.
     * @param durations        A list of {@code WorkDuration} associated with the tracked item.
     * @param timer            A timer associated with the tracked item.
     */
    public TrackedItem(Name name, Description description, CompletionStatus completionStatus, Date createdDate,
                       Deadline deadline, Set<Tag> tags, UniqueDurationList durations, Timer timer) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.tags.addAll(tags);
        this.durations = durations;
        this.timer = timer;
    }

    /**
     * Constructs a new {@code TrackedItem}
     *
     * @param name             A valid name.
     * @param completionStatus A completion status of the tracked item.
     * @param createdDate      A date associated with the creation of the tracked item
     * @param deadline         A deadline associated with the tracked item.
     * @param description      A description of the tracked item.
     * @param tags             A set of tags associated to the tracked item.
     */
    public TrackedItem(Name name, Description description, CompletionStatus completionStatus, Date createdDate,
                       Deadline deadline, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.tags.addAll(tags);
        this.durations = new UniqueDurationList();
        this.timer = new Timer();
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    /**
     * Gets Deadline and name for {@code DeadLineCompare} comparator.
     * Returns null if deadline is empty as {@code Comparator.nullsLast} method is used.
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an unmodifiable view of the durations list.
     */
    public ObservableList<WorkDuration> getDurationList() {
        return durations.asUnmodifiableObservableList();
    }

    /**
     * Returns a copy of this tracked item with its timer started.
     *
     * @return A copy of this tracked item, but with its timer started
     */
    public abstract TrackedItem startTimer();

    /**
     * Returns a copy of this tracked item with its timer stopped, then adds the timed duration into
     * the list.
     *
     * @return A copy of this tracked item, but with its timer stopped
     */
    public abstract TrackedItem stopTimer();

    public Timer getTimer() {
        return timer;
    }

    /**
     * Checks if the tracked item's timer is currently running.
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    /**
     * Returns true if both tracked item of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two projects.
     */
    public boolean isSameTrackedItem(TrackedItem otherTrackedItem) {
        if (otherTrackedItem == this) {
            return true;
        }

        return otherTrackedItem != null
                && otherTrackedItem.getName().equals(getName())
                && otherTrackedItem.getDescription().equals(getDescription())
                && otherTrackedItem.getCompletionStatus().equals(getCompletionStatus())
                && otherTrackedItem.getCreatedDate().equals(getCreatedDate())
                && otherTrackedItem.getDeadline().equals(getDeadline());
    }

    /**
     * Returns true if the instance is a Task. Returns false otherwise.
     */
    public abstract boolean isTask();

    /**
     * Returns true if both tracked items have the same identity and data fields.
     * This defines a stronger notion of equality between two tracked items.
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
                && otherTrackedItem.getCreatedDate().equals(getCreatedDate())
                && otherTrackedItem.getDeadline().equals(getDeadline())
                && otherTrackedItem.getTags().equals(getTags())
                && otherTrackedItem.getDurationList().equals(getDurationList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, completionStatus, createdDate, deadline, tags, durations, timer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append("Completion Status:")
                .append(getCompletionStatus())
                .append(" Created Date: ")
                .append(getCreatedDate())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
