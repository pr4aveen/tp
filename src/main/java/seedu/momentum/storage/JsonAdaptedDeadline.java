package seedu.momentum.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Time;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.Deadline;

/**
 * Jackson-friendly version of {@link Deadline}.
 */
class JsonAdaptedDeadline {

    private final String date;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given date and time.
     */
    @JsonCreator
    public JsonAdaptedDeadline(@JsonProperty("date") String date,
                               @JsonProperty("time") String time) {
        this.date = date;
        this.time = time;
    }

    /**
     * Converts a given {@code Deadline} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        if (!source.isEmpty()) {
            this.date = source.getDate().toString();
        } else {
            this.date = null;
        }
        if (source.hasTime()) {
            this.time = source.getTime().toString();
        } else {
            this.time = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted duration object into the model's {@code Deadline} object.
     *
     * @param createdDate the created date of the project.
     * @throws IllegalValueException if there were any data constraints violated in the adapted duration.
     */
    public Deadline toModelType(Date createdDate) throws IllegalValueException {
        if (this.date == null) {
            return new Deadline();
        }
        if (!Date.isValid(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        if (this.time == null) {
            return new Deadline(date, createdDate);
        }
        if (!Time.isValid(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }

        return new Deadline(date, time, createdDate);
    }

}
