package seedu.momentum.model.timer;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.momentum.model.project.Project;

/**
 * Represents a timer in momentum.
 */
public class Timer {
    private Time startTime;
    private Time stopTime;
    private Project project;

    /**
     * Create a timer that is tracker a specific project.
     * @param project The project being tracked.
     */
    public Timer(Project project) {
        requireNonNull(project);
        this.project = project;
    }

    /**
     * Start the timer.
     */
    public void start() {
        startTime = new Time(LocalDateTime.now());
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        stopTime = new Time(LocalDateTime.now());
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getStopTime() {
        return stopTime;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timer timer = (Timer) o;
        return project.equals(timer.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }
}
