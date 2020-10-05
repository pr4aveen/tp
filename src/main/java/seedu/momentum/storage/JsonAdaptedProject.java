package seedu.momentum.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String name;
    private final String description;
    private final String createdDate;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedWorkDuration> durations = new ArrayList<>();
    private final JsonAdaptedTimer timer;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("createdDate") String createdDate,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                              @JsonProperty("durations") List<JsonAdaptedWorkDuration> durations,
                              @JsonProperty("timer") JsonAdaptedTimer timer) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (durations != null) {
            this.durations.addAll(durations);
        }
        this.timer = timer;
    }

    /**
     * Converts a given {@code Project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.getName().fullName;
        description = source.getDescription().value;
        createdDate = source.getCreatedDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        durations.addAll(source.getDurationList().stream()
                .map(JsonAdaptedWorkDuration::new)
                .collect(Collectors.toList()));
        timer = new JsonAdaptedTimer(source.getTimer());
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException {
        final List<Tag> projectTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            projectTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Description modelDescription = new Description(description);

        if (!Date.isValidDate(createdDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelCreatedDate = new Date(createdDate);

        final Set<Tag> modelTags = new HashSet<>(projectTags);

        final List<WorkDuration> projectDurations = new ArrayList<>();
        for (JsonAdaptedWorkDuration duration : durations) {
            projectDurations.add(duration.toModelType());
        }

        UniqueDurationList modelDurations = new UniqueDurationList();
        modelDurations.setDurations(projectDurations);

        final Timer modelTimer = timer == null ? new Timer() : timer.toModelType();

        return new Project(modelName, modelDescription, modelCreatedDate, modelTags, modelDurations, modelTimer);
    }

}
