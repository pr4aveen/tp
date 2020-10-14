package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.TimeUtil;

public class TimeTest {
    private static final String VALID_TIME = "10:15:30";
    private static final String VALID_LATER_TIME = "10:15:35";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time((String) null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "asfd";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time time
        assertThrows(NullPointerException.class, () -> Time.isValid(null));

        // invalid time time
        assertFalse(Time.isValid("")); // empty string
        assertFalse(Time.isValid(" ")); // spaces only
        assertFalse(Time.isValid("^")); // only non-alphanumeric characters
        assertFalse(Time.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(Time.isValid("10:15:30*")); // wrong format
        assertFalse(Time.isValid("52:15:30")); // not a real time

        // valid time time
        assertTrue(Time.isValid(VALID_TIME)); // typical time
    }

    @Test
    public void getFormattedTime_formatsCorrectly() {
        Time time = new Time(VALID_TIME);
        assertEquals(time.get().format(TimeUtil.FORMAT_TIME_MEDIUM), time.getFormatted());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second Time is later
        assertEquals(new Time(VALID_TIME).compareTo(new Time(VALID_LATER_TIME)), -1);

        // second Time is earlier
        assertEquals(new Time(VALID_LATER_TIME).compareTo(new Time(VALID_TIME)), 1);

        // both Time same time
        assertEquals(new Time(VALID_TIME).compareTo(new Time("10:15:30")), 0);
    }
}
