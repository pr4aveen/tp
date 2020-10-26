package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.DateTimeUtil;

public class DateTimeWrapperTest {

    private static final String VALID_DATE_TIME = "2020-09-23T16:55:12.83012";
    private static final String VALID_LATER_DATE_TIME = "2020-10-23T16:55:12.83012";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTimeWrapper((String) null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "asfd";
        assertThrows(IllegalArgumentException.class, () -> new DateTimeWrapper(invalidDateTime));
    }

    @Test
    public void isValidDateTime() {
        // null time
        assertThrows(NullPointerException.class, () -> DateTimeWrapper.isValid(null));

        // invalid time
        assertFalse(DateTimeWrapper.isValid("")); // empty string
        assertFalse(DateTimeWrapper.isValid(" ")); // spaces only
        assertFalse(DateTimeWrapper.isValid("^")); // only non-alphanumeric characters
        assertFalse(DateTimeWrapper.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(DateTimeWrapper.isValid("12/02/12*")); // wrong format
        assertFalse(DateTimeWrapper.isValid("2020-30-23T16:55:12.83012")); // not a real date

        // valid time
        assertTrue(DateTimeWrapper.isValid(VALID_DATE_TIME)); // typical date time
    }

    @Test
    public void toString_formatsCorrectly() {
        DateTimeWrapper dateTimeWrapper = new DateTimeWrapper("2020-09-23T16:55:12.83012");
        assertEquals(dateTimeWrapper.get().format(DateTimeUtil.FORMAT_DATE_TIME_MEDIUM),
                dateTimeWrapper.getFormatted());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second TimeWrapper is later
        assertEquals(new DateTimeWrapper(VALID_DATE_TIME).compareTo(new DateTimeWrapper(VALID_LATER_DATE_TIME)), -1);

        // second TimeWrapper is earlier
        assertEquals(new DateTimeWrapper(VALID_LATER_DATE_TIME).compareTo(new DateTimeWrapper(VALID_DATE_TIME)), 1);

        // both TimeWrapper same time
        assertEquals(new DateTimeWrapper(VALID_DATE_TIME)
                .compareTo(new DateTimeWrapper("2020-09-23T16:55:12.83012")), 0);
    }
}
