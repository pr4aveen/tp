package seedu.momentum.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String name;
    private final String description;
    private final boolean completionStatus;
    private final String createdDate;
    private final JsonAdaptedDeadline deadline;
    private final String reminder;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedWorkDuration> durations = new ArrayList<>();
    private final JsonAdaptedTimer timer;

    /**
     * Constructs a {@code Task} with the given details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("completionStatus") boolean completionStatus,
                           @JsonProperty("createdDateWrapper") String createdDate,
                           @JsonProperty("deadline") JsonAdaptedDeadline deadline,
                           @JsonProperty("reminder") String reminder,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("durations") List<JsonAdaptedWorkDuration> durations,
                           @JsonProperty("timer") JsonAdaptedTimer timer) {
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
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(TrackedItem source) {
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
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final DateWrapper modelCreatedDateWrapper = JsonToModel.getModelCreatedDate(createdDate);

        return new Task(JsonToModel.getModelName(name, MISSING_FIELD_MESSAGE_FORMAT),
                JsonToModel.getModelDescription(description),
                JsonToModel.getModelCompletionStatus(completionStatus),
                modelCreatedDateWrapper,
                JsonToModel.getModelDeadline(deadline, modelCreatedDateWrapper),
                JsonToModel.getModelReminder(reminder),
                JsonToModel.getModelTags(tagged),
                JsonToModel.getModelDurations(durations),
                JsonToModel.getModelTimerWrapper(timer));
    }

}
