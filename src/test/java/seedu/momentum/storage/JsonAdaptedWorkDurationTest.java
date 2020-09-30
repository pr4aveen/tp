package seedu.momentum.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.storage.JsonAdaptedWorkDuration.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.timer.Time;
import seedu.momentum.testutil.TypicalWorkDuration;

public class JsonAdaptedWorkDurationTest {
    private static final String VALID_TIME = "2020-09-23T16:55:12.83012";
    private static final String INVALID_TIME = "as;dlkfj";

    private static final List<JsonAdaptedWorkDuration> VALID_DURATIONS =
            BENSON.getDurationList().stream().map(JsonAdaptedWorkDuration::new).collect(Collectors.toList());

    @Test
    public void toModelType_validDurationDetails_returnsDuration() throws Exception {
        JsonAdaptedWorkDuration duration = new JsonAdaptedWorkDuration(TypicalWorkDuration.DURATION_ONE_DAY);

        assertEquals(TypicalWorkDuration.DURATION_ONE_DAY.getStartTime().getTime().getSecond(),
                duration.toModelType().getStartTime().getTime().getSecond());
        assertEquals(TypicalWorkDuration.DURATION_ONE_DAY.getStopTime().getTime().getSecond(),
                duration.toModelType().getStopTime().getTime().getSecond());
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedWorkDuration duration =
                new JsonAdaptedWorkDuration(INVALID_TIME, VALID_TIME);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, duration::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedWorkDuration duration = new JsonAdaptedWorkDuration(null, VALID_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, duration::toModelType);
    }

    @Test
    public void toModelType_invalidStopTime_throwsIllegalValueException() {
        JsonAdaptedWorkDuration duration =
                new JsonAdaptedWorkDuration(VALID_TIME, INVALID_TIME);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, duration::toModelType);
    }

    @Test
    public void toModelType_nullStopTime_throwsIllegalValueException() {
        JsonAdaptedWorkDuration duration =
                new JsonAdaptedWorkDuration(VALID_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, duration::toModelType);
    }

}
