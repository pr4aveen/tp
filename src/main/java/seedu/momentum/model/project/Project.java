package seedu.momentum.model.project;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Project in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Project {

    // Identity fields
    private final Name name;

    private final Set<Tag> tags = new HashSet<>();

    private final UniqueDurationList durations;

    /**
     * Constructs a {@code Project}.
     *
     * @param name A valid name.
     * @param tags A set of tags associated to the project.
     * @param durations A list of {@code WorkDuration} associated with the project
     */
    public Project(Name name, Set<Tag> tags, UniqueDurationList durations) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.tags.addAll(tags);
        this.durations = durations;
    }

    /**
     * Constructs a new {@code Project}
     *
     * @param name A valid name.
     * @param tags A set of tags associated to the project.
     */
    public Project(Name name, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.tags.addAll(tags);
        this.durations = new UniqueDurationList();
    }

    public Name getName() {
        return name;
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
     * Adds a WorkDuration to the duration list.
     * Duplicates will not be added.
     *
     * @param duration
     */
    public void addDuration(WorkDuration duration) {
        durations.add(duration);
    }

    /**
     * Returns true if both projects of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two projects.
     */
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.getName().equals(getName());
    }

    /**
     * Returns true if both projects have the same identity and data fields.
     * This defines a stronger notion of equality between two projects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Project)) {
            return false;
        }

        Project otherProject = (Project) other;
        return otherProject.getName().equals(getName())
                && otherProject.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
