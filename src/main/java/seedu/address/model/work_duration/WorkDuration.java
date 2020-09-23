package seedu.address.model.work_duration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import seedu.address.model.project.Project;

public class WorkDuration {
    private final LocalDateTime startTime;
    private final LocalDateTime stopTime;
    private final Project project;

    public WorkDuration(Project project) {
        this(project, LocalDateTime.now(), LocalDateTime.now());
    }

    public WorkDuration(Project project, LocalDateTime startTime, LocalDateTime stopTime) {
        this.project = project;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public WorkDuration(Project project, LocalDateTime startTime) {
        this(project, startTime, LocalDateTime.now());
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public Project getProject() {
        return project;
    }

    public long getTimeBetween(ChronoUnit unit) {
        return unit.between(startTime, stopTime);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameDuration(WorkDuration otherDuration) {
        if (otherDuration == this) {
            return true;
        }

        return otherDuration != null
                && otherDuration.getStartTime().equals(getStartTime())
                && otherDuration.getStopTime().equals(getStopTime());
    }
}
