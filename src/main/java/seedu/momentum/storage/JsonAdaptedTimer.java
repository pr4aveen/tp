package seedu.momentum.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.DateTime;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.commons.util.DateTimeUtil;
import seedu.momentum.model.timer.Timer;

/**
 * Jackson-friendly version of {@link Timer}.
 */
class JsonAdaptedTimer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Duration's %s field is missing!";

    private final String startTime;
    private final String stopTime;
    private final Boolean isRunning;

    /**
     * Constructs a {@code JsonAdaptedWorkDuration} with the given duration details.
     */
    @JsonCreator
    public JsonAdaptedTimer(@JsonProperty("startTime") String startTime,
                                   @JsonProperty("stopTime") String stopTime,
                                   @JsonProperty("isRunning") Boolean isRunning) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.isRunning = isRunning;
    }

    /**
     * Converts a given {@code WorkDuration} into this class for Jackson use.
     */
    public JsonAdaptedTimer(Timer source) {
        startTime = source.getStartTime().get().format(DateTimeUtil.FORMAT_DATA);
        stopTime = source.getStopTime().get().format(DateTimeUtil.FORMAT_DATA);
        isRunning = source.isRunning();
    }

    /**
     * Converts this Jackson-friendly adapted duration object into the model's {@code Timer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted duration.
     */
    public Timer toModelType() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }

        if (!DateTime.isValid(startTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }

        final DateTime modelStartDateTime = new DateTime(startTime);

        if (stopTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }

        if (!DateTime.isValid(stopTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }

        final DateTime modelStopDateTime = new DateTime(stopTime);

        if (isRunning == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isRunning"));
        }

        return new Timer(modelStartDateTime, modelStopDateTime, isRunning);
    }

}
