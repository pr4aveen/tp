package seedu.momentum.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.storage.JsonAdaptedTimer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.timer.TimerWrapper;

public class JsonAdaptedTimerWrapperTest {
    private static final String VALID_TIME = "2020-09-23T16:55:12.83012";
    private static final String INVALID_TIME = "as;dlkfj";

    @Test
    public void toModelType_validTimerDetails_returnsTimer() throws Exception {
        JsonAdaptedTimer timer = new JsonAdaptedTimer(VALID_TIME, VALID_TIME, false);
        TimerWrapper expectedTimerWrapper =
                new TimerWrapper(new DateTimeWrapper(VALID_TIME), new DateTimeWrapper(VALID_TIME), false);
        assertEquals(timer.toModelType(), expectedTimerWrapper);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedTimer timer = new JsonAdaptedTimer(INVALID_TIME, VALID_TIME, false);
        String expectedMessage = DateTimeWrapper.MESSAGE_CONSTRAINTS;

        assertThrows(IllegalValueException.class, expectedMessage, timer::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedTimer timer = new JsonAdaptedTimer(null, VALID_TIME, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTimeWrapper.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, timer::toModelType);
    }

    @Test
    public void toModelType_invalidStopTime_throwsIllegalValueException() {
        JsonAdaptedTimer timer = new JsonAdaptedTimer(VALID_TIME, INVALID_TIME, false);
        String expectedMessage = DateTimeWrapper.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, timer::toModelType);
    }

    @Test
    public void toModelType_nullStopTime_throwsIllegalValueException() {
        JsonAdaptedTimer timer = new JsonAdaptedTimer(VALID_TIME, null, false);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTimeWrapper.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, timer::toModelType);
    }

    @Test
    public void toModelType_nullIsRunning_throwsIllegalValueException() {
        JsonAdaptedTimer timer = new JsonAdaptedTimer(VALID_TIME, VALID_TIME, null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "isRunning");
        assertThrows(IllegalValueException.class, expectedMessage, timer::toModelType);
    }

}
