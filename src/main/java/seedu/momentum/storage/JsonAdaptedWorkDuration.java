//@@author boundtotheearth
package seedu.momentum.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.commons.util.DateTimeUtil;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Jackson-friendly version of {@link WorkDuration}.
 */
class JsonAdaptedWorkDuration {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Duration's %s field is missing!";

    private final String startTime;
    private final String stopTime;

    /**
     * Constructs a {@code JsonAdaptedWorkDuration} with the given duration details.
     *
     * @param startTime Start time of the work duration.
     * @param stopTime Stop time of the work duration.
     */
    @JsonCreator
    public JsonAdaptedWorkDuration(@JsonProperty("startTime") String startTime,
                              @JsonProperty("stopTime") String stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    /**
     * Converts a given {@code WorkDuration} into this class for Jackson use.
     *
     * @param source WorkDuration object containing the start and stop times.
     */
    public JsonAdaptedWorkDuration(WorkDuration source) {
        startTime = source.getStartTime().get().format(DateTimeUtil.FORMAT_DATA);
        stopTime = source.getStopTime().get().format(DateTimeUtil.FORMAT_DATA);
    }

    /**
     * Converts this Jackson-friendly adapted duration object into the model's {@code WorkDuration} object.
     *
     * @return The converted WorkDuration object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted duration.
     */
    public WorkDuration toModelType() throws IllegalValueException {
        final DateTimeWrapper modelStartDateTime =
            JsonToModel.getModelDateTimeWrapper(startTime, MISSING_FIELD_MESSAGE_FORMAT);

        final DateTimeWrapper modelStopDateTime =
            JsonToModel.getModelDateTimeWrapper(stopTime, MISSING_FIELD_MESSAGE_FORMAT);

        return new WorkDuration(modelStartDateTime, modelStopDateTime);
    }

}
