package seedu.momentum.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Date;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;
import seedu.momentum.model.util.SampleDataUtil;

/**
 * A utility class to help with building Project objects.
 */
public class ProjectBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DESCRIPTION = "Likes coding";
    public static final String DEFAULT_CREATED_DATE = "2019-11-05";
    public static final String DEFAULT_DEADLINE_DATE = "2020-11-05";
    public static final String DEFAULT_DEADLINE_TIME = "11:11:11";

    private Name name;
    private Description description;
    private Date createdDate;
    private Deadline deadline;
    private Set<Tag> tags;
    private UniqueDurationList durations;
    private Timer timer;

    /**
     * Creates a {@code ProjectBuilder} with the default details.
     */
    public ProjectBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        createdDate = new Date(DEFAULT_CREATED_DATE);
        deadline = new Deadline(DEFAULT_DEADLINE_DATE, DEFAULT_DEADLINE_TIME);
        tags = new HashSet<>();
        durations = new UniqueDurationList();
        timer = new Timer();
    }

    /**
     * Initializes the ProjectBuilder with the data of {@code projectToCopy}.
     */
    public ProjectBuilder(Project projectToCopy) {
        name = projectToCopy.getName();
        description = projectToCopy.getDescription();
        createdDate = projectToCopy.getCreatedDate();
        deadline = projectToCopy.getDeadline();
        tags = new HashSet<>(projectToCopy.getTags());
        durations = new UniqueDurationList();
        for (WorkDuration duration : projectToCopy.getDurationList()) {
            durations.add(duration);
        }
        timer = projectToCopy.getTimer();
    }

    /**
     * Sets the {@code Name} of the {@code Project} that we are building.
     */
    public ProjectBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Project} that we are building.
     */
    public ProjectBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Project} that we are building to an empty string.
     */
    public ProjectBuilder withEmptyDescription() {
        this.description = Description.EMPTY_DESCRIPTION;
        return this;
    }

    /**
     * Sets the {@code CreatedDate} of the {@code Project} that we are building with current date.
     */
    public ProjectBuilder withCurrentCreatedDate() {
        this.createdDate = new Date(Clock.now().getDate());
        return this;
    }

    /**
     * Sets the {@code CreatedDate} of the {@code Project} that we are building.
     */
    public ProjectBuilder withCreatedDate(String createdDate) {
        this.createdDate = new Date(createdDate);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Project} that we are building with an empty deadline.
     */
    public ProjectBuilder withEmptyDeadline() {
        this.deadline = new Deadline();
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Project} that we are building.
     */
    public ProjectBuilder withDeadline(String date) {
        this.deadline = new Deadline(date);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Project} that we are building.
     */
    public ProjectBuilder withDeadline(String date, String time) {
        this.deadline = new Deadline(date, time);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Project} that we are building.
     */
    public ProjectBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code durations} into a {@code UniqueDurationList} and set it to the {@code Project} that we
     * are building.
     */
    public ProjectBuilder withDurations(WorkDuration... durations) {
        this.durations = SampleDataUtil.getDurationList(durations);
        return this;
    }

    /**
     * Parses the {@code timer} into a {@code Timer} and set it to the {@code Project} that we
     * are building.
     */
    public ProjectBuilder withTimer(Timer timer) {
        this.timer = timer;
        return this;
    }

    public Project build() {
        return new Project(name, description, createdDate, deadline, tags, durations, timer);
    }
}
