package seedu.momentum.model.project;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.Date;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Project in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Project extends TrackedItem {

    /**
     * Constructs a {@code Project}.
     *
     * @param name A valid name.
     * @param description A description of the project.
     * @param createdDate A date associated with the creation of the project.
     * @param deadline A deadline associated with the project.
     * @param tags A set of tags associated to the project.
     * @param durations A list of {@code WorkDuration} associated with the project.
     * @param timer A timer associated with the project.
     */
    public Project(Name name, Description description, Date createdDate, Deadline deadline,
                   Set<Tag> tags, UniqueDurationList durations, Timer timer) {
        super(name, description, createdDate, deadline, tags, durations, timer);
    }

    /**
     * Constructs a new {@code Project}
     *
     * @param name A valid name.
     * @param createdDate A date associated with the creation of the project
     * @param deadline A deadline associated with the project.
     * @param description A description of the project.
     * @param tags A set of tags associated to the project.
     */
    public Project(Name name, Description description, Date createdDate, Deadline deadline, Set<Tag> tags) {
        super(name, description, createdDate, deadline, tags);
    }

    /**
     * Returns a copy of this project with its timer started.
     *
     * @return A copy of this project, but with its timer started
     */
    @Override
    public Project startTimer() {
        Timer newTimer = timer.start();
        return new Project(name, description, createdDate, deadline, tags, durations, newTimer);
    }

    /**
     * Returns a copy of this project with its timer stopped, then adds the timed duration into
     * the list.
     *
     * @return A copy of this project, but with its timer stopped
     */
    @Override
    public Project stopTimer() {
        Timer newTimer = timer.stop();
        WorkDuration duration = new WorkDuration(newTimer.getStartTime(), newTimer.getStopTime());
        UniqueDurationList newDurations = new UniqueDurationList();
        newDurations.setDurations(durations);
        newDurations.add(duration);
        return new Project(name, description, createdDate, deadline, tags, newDurations, newTimer);
    }

    /**
     * Returns true if both tracked item of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two projects.
     */
    @Override
    public boolean isSameTrackedItem(TrackedItem otherTrackedItem) {
        if (!(otherTrackedItem instanceof Project)) {
            return false;
        }

        return super.isSameTrackedItem(otherTrackedItem);
    }

    /**
     * Returns true if the instance is a Task. Returns false otherwise.
     */
    @Override
    public boolean isTask() {
        return false;
    }

    /**
     * Returns true if both tracked items have the same identity and data fields.
     * This defines a stronger notion of equality between two tracked items.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Project)) {
            return false;
        }

        return super.equals(other);
    }

    //Temporary for testing
    public ObservableList<TrackedItem> getTaskList() {
        return FXCollections.observableArrayList();
    }
}
