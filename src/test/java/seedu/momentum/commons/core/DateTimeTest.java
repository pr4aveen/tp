package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.DateTimeUtil;

public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime((String) null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "asfd";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void isValidDateTime() {
        // null time
        assertThrows(NullPointerException.class, () -> DateTime.isValid(null));

        // invalid time
        assertFalse(DateTime.isValid("")); // empty string
        assertFalse(DateTime.isValid(" ")); // spaces only
        assertFalse(DateTime.isValid("^")); // only non-alphanumeric characters
        assertFalse(DateTime.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(DateTime.isValid("12/02/12*")); // wrong format
        assertFalse(DateTime.isValid("2020-30-23T16:55:12.83012")); // not a real date

        // valid time
        assertTrue(DateTime.isValid("2020-09-23T16:55:12.83012")); // typical date time
    }

    @Test
    public void toString_formatsCorrectly() {
        DateTime dateTime = new DateTime("2020-09-23T16:55:12.83012");
        assertEquals(dateTime.get().format(DateTimeUtil.FORMAT_DATE_TIME_MEDIUM), dateTime.getFormatted());
    }
}
