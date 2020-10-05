package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.TimeUtil;

public class TimeTest {
    private static final String VALID_TIME = "10:15:30";

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
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("^")); // only non-alphanumeric characters
        assertFalse(Time.isValidTime("peter*")); // contains non-alphanumeric characters
        assertFalse(Time.isValidTime("10:15:30*")); // wrong format
        assertFalse(Time.isValidTime("52:15:30")); // not a real time

        // valid time time
        assertTrue(Time.isValidTime(VALID_TIME)); // typical time
    }

    @Test
    public void getFormattedTime_formatsCorrectly() {
        Time time = new Time(VALID_TIME);
        assertEquals(time.getTime().format(TimeUtil.FORMAT_TIME_MEDIUM), time.getFormattedTime());
    }
}
