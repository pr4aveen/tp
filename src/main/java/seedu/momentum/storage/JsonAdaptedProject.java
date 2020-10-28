package seedu.momentum.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.Project;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String name;
    private final String description;
    private final boolean completionStatus;
    private final String createdDate;
    private final JsonAdaptedDeadline deadline;
    private final String reminder;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedWorkDuration> durations = new ArrayList<>();
    private final JsonAdaptedTimer timer;
    private final List<JsonAdaptedTask> taskList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("completionStatus") boolean completionStatus,
                              @JsonProperty("createdDateWrapper") String createdDate,
                              @JsonProperty("deadline") JsonAdaptedDeadline deadline,
                              @JsonProperty("reminder") String reminder,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                              @JsonProperty("durations") List<JsonAdaptedWorkDuration> durations,
                              @JsonProperty("timer") JsonAdaptedTimer timer,
                              @JsonProperty("taskList") List<JsonAdaptedTask> taskList) {
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.reminder = reminder;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (durations != null) {
            this.durations.addAll(durations);
        }
        this.timer = timer;
        if (taskList != null) {
            this.taskList.addAll(taskList);
        }
    }

    public JsonAdaptedProject(String name,
                              String description,
                              boolean completionStatus,
                              String createdDate,
                              JsonAdaptedDeadline deadline,
                              String reminder,
                              List<JsonAdaptedTag> tagged,
                              List<JsonAdaptedWorkDuration> durations,
                              JsonAdaptedTimer timer) {
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.reminder = reminder;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (durations != null) {
            this.durations.addAll(durations);
        }
        this.timer = timer;
    }

    /**
     * Converts a given {@code TrackedItem} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.getName().fullName;
        description = source.getDescription().value;
        completionStatus = source.getCompletionStatus().isCompleted();
        createdDate = source.getCreatedDate().toString();
        deadline = new JsonAdaptedDeadline(source.getDeadline());
        reminder = source.getReminder().isEmpty() ? null : source.getReminder().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        durations.addAll(source.getDurationList().stream()
                .map(JsonAdaptedWorkDuration::new)
                .collect(Collectors.toList()));
        timer = new JsonAdaptedTimer(source.getTimer());
        taskList.addAll(source.getTaskList().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code TrackedItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tracked item.
     */
    public Project toModelType() throws IllegalValueException {
        final DateWrapper modelCreatedDateWrapper = JsonToModel.getModelCreatedDate(createdDate);

        return new Project(JsonToModel.getModelName(name, MISSING_FIELD_MESSAGE_FORMAT),
                JsonToModel.getModelDescription(description),
                JsonToModel.getModelCompletionStatus(completionStatus),
                modelCreatedDateWrapper,
                JsonToModel.getModelDeadline(deadline, modelCreatedDateWrapper),
                JsonToModel.getModelReminder(reminder, modelCreatedDateWrapper),
                JsonToModel.getModelTags(tagged),
                JsonToModel.getModelDurations(durations),
                JsonToModel.getModelTimerWrapper(timer),
                JsonToModel.getModelTasks(taskList));
    }

}
