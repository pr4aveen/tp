package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.timer.DurationUtil;
import seedu.momentum.model.timer.Time;

public class TimeTest {

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
    public void isValidName() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("^")); // only non-alphanumeric characters
        assertFalse(Time.isValidTime("peter*")); // contains non-alphanumeric characters
        assertFalse(Time.isValidTime("12/02/12*")); // wrong format
        assertFalse(Time.isValidTime("2020-30-23T16:55:12.83012")); // not a real date

        // valid time
        assertTrue(Time.isValidTime("2020-09-23T16:55:12.83012")); // typical time
    }

    @Test
    public void toString_formatsCorrectly() {
        Time time = new Time("2020-09-23T16:55:12.83012");
        assertEquals(time.getTime().format(DurationUtil.FORMAT_DATE_TIME), time.toString());
    }
}
