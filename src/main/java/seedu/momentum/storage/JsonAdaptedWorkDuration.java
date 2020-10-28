package seedu.momentum.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.commons.util.DateTimeUtil;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedWorkDuration {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Duration's %s field is missing!";

    private final String startTime;
    private final String stopTime;

    /**
     * Constructs a {@code JsonAdaptedWorkDuration} with the given duration details.
     */
    @JsonCreator
    public JsonAdaptedWorkDuration(@JsonProperty("startTime") String startTime,
                              @JsonProperty("stopTime") String stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    /**
     * Converts a given {@code WorkDuration} into this class for Jackson use.
     */
    public JsonAdaptedWorkDuration(WorkDuration source) {
        startTime = source.getStartTime().get().format(DateTimeUtil.FORMAT_DATA);
        stopTime = source.getStopTime().get().format(DateTimeUtil.FORMAT_DATA);
    }

    /**
     * Converts this Jackson-friendly adapted duration object into the model's {@code WorkDuration} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted duration.
     */
    public WorkDuration toModelType() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTimeWrapper.class.getSimpleName()));
        }

        if (!DateTimeWrapper.isValid(startTime)) {
            throw new IllegalValueException(DateTimeWrapper.MESSAGE_CONSTRAINTS);
        }

        final DateTimeWrapper modelStartDateTime = new DateTimeWrapper(startTime);

        if (stopTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTimeWrapper.class.getSimpleName()));
        }

        if (!DateTimeWrapper.isValid(stopTime)) {
            throw new IllegalValueException(DateTimeWrapper.MESSAGE_CONSTRAINTS);
        }

        final DateTimeWrapper modelStopDateTime = new DateTimeWrapper(stopTime);

        return new WorkDuration(modelStartDateTime, modelStopDateTime);
    }

}
