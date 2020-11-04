package seedu.momentum.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.commons.util.DateTimeUtil;
import seedu.momentum.model.timer.TimerWrapper;

/**
 * Jackson-friendly version of {@link TimerWrapper}.
 */
class JsonAdaptedTimer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Timer's %s field is missing!";

    private final String startTime;
    private final String stopTime;
    private final Boolean isRunning;

    /**
     * Constructs a {@code TimerWrapper} with the given duration details.
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
     * Converts a given {@code TimerWrapper} into this class for Jackson use.
     */
    public JsonAdaptedTimer(TimerWrapper source) {
        startTime = source.getStartTime().get().format(DateTimeUtil.FORMAT_DATA);
        stopTime = source.getStopTime().get().format(DateTimeUtil.FORMAT_DATA);
        isRunning = source.isRunning();
    }

    /**
     * Converts this Jackson-friendly adapted duration object into the model's {@code TimerWrapper} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timerWrapper.
     */
    public TimerWrapper toModelType() throws IllegalValueException {
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

        if (isRunning == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isRunning"));
        }

        return new TimerWrapper(modelStartDateTime, modelStopDateTime, isRunning);
    }

}
