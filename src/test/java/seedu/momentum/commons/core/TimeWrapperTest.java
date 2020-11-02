package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.TimeUtil;

public class TimeWrapperTest {

    private static final String VALID_TIME = "10:15:30";
    private static final String VALID_LATER_TIME = "10:15:35";

    private static final TimeWrapper TIME = new TimeWrapper(VALID_TIME);
    private static final TimeWrapper LATER_TIME = new TimeWrapper(VALID_LATER_TIME);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TimeWrapper((String) null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "asfd";
        assertThrows(IllegalArgumentException.class, () -> new TimeWrapper(invalidTime));
    }

    @Test
    public void isValid() {
        // null time
        assertThrows(NullPointerException.class, () -> TimeWrapper.isValid(null));

        // invalid time
        assertFalse(TimeWrapper.isValid("")); // empty string
        assertFalse(TimeWrapper.isValid(" ")); // spaces only
        assertFalse(TimeWrapper.isValid("^")); // only non-alphanumeric characters
        assertFalse(TimeWrapper.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(TimeWrapper.isValid("10:15:30*")); // wrong format
        assertFalse(TimeWrapper.isValid("52:15:30")); // not a real time

        // valid time
        assertTrue(TimeWrapper.isValid(VALID_TIME)); // typical time
    }

    @Test
    public void getFormattedTime_formatsCorrectly() {
        TimeWrapper timeWrapper = TIME;
        assertEquals(timeWrapper.get().format(TimeUtil.FORMAT_TIME_MEDIUM), timeWrapper.getFormatted());
    }

    @Test
    public void hashCodeTest() {
        // both TimeWrapper same time
        assertEquals(new TimeWrapper(LocalTime.parse(VALID_TIME)).hashCode(), TIME.hashCode());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second TimeWrapper is later
        assertEquals(TIME.compareTo(LATER_TIME), -1);

        // second TimeWrapper is earlier
        assertEquals(LATER_TIME.compareTo(TIME), 1);

        // both TimeWrapper same time
        assertEquals(TIME.compareTo(new TimeWrapper("10:15:30")), 0);
    }
}
