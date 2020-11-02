package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.DateTimeUtil;

public class DateTimeWrapperTest {

    private static final String VALID_DATE_TIME = "2020-09-23T16:55:12";
    private static final String VALID_LATER_DATE_TIME = "2020-10-23T16:55:12";

    private static final DateTimeWrapper DATE_TIME = new DateTimeWrapper(VALID_DATE_TIME);
    private static final DateTimeWrapper LATER_DATE_TIME = new DateTimeWrapper(VALID_LATER_DATE_TIME);

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
    public void isValid() {
        // null time
        assertThrows(NullPointerException.class, () -> DateTimeWrapper.isValid(null));

        // invalid time
        assertFalse(DateTimeWrapper.isValid("")); // empty string
        assertFalse(DateTimeWrapper.isValid(" ")); // spaces only
        assertFalse(DateTimeWrapper.isValid("^")); // only non-alphanumeric characters
        assertFalse(DateTimeWrapper.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(DateTimeWrapper.isValid("12/02/12*")); // wrong format
        assertFalse(DateTimeWrapper.isValid("2020-30-23T16:55:12")); // not a real date

        // valid time
        assertTrue(DateTimeWrapper.isValid(VALID_DATE_TIME)); // typical date time
    }

    @Test
    public void getTimeBetween() {
        assertEquals(30, DateTimeWrapper.getTimeBetween(DATE_TIME, LATER_DATE_TIME, ChronoUnit.DAYS));
        assertEquals(-4, DateTimeWrapper.getTimeBetween(LATER_DATE_TIME, DATE_TIME, ChronoUnit.WEEKS));
    }

    @Test
    public void getDateWrapper() {
        assertEquals(new DateWrapper("2020-09-23"), DATE_TIME.getDateWrapper());
    }

    @Test
    public void getTimeWrapper() {
        assertEquals(new TimeWrapper("16:55:12"), DATE_TIME.getTimeWrapper());
    }

    @Test
    public void toString_formatsCorrectly() {
        DateTimeWrapper dateTimeWrapper = new DateTimeWrapper("2020-09-23T16:55:12");
        assertEquals(dateTimeWrapper.get().format(DateTimeUtil.FORMAT_DATE_TIME_MEDIUM),
                dateTimeWrapper.getFormatted());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(DATE_TIME.hashCode(), new DateTimeWrapper(VALID_DATE_TIME).hashCode());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(DATE_TIME.equals(DATE_TIME));

        // same date and time -> returns true
        assertTrue(DATE_TIME.equals(new DateWrapper(VALID_DATE_TIME)));

        // different types -> returns false
        assertFalse(DATE_TIME.equals("1"));

        // null -> return false
        assertFalse(DATE_TIME.equals(null));

        // different date -> return false
        assertFalse(DATE_TIME.equals(LATER_DATE_TIME));
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second TimeWrapper is later
        assertEquals(DATE_TIME.compareTo(LATER_DATE_TIME), -1);

        // second TimeWrapper is earlier
        assertEquals(LATER_DATE_TIME.compareTo(DATE_TIME), 1);

        // both TimeWrapper same time
        assertEquals(DATE_TIME.compareTo(new DateTimeWrapper("2020-09-23T16:55:12")), 0);
    }
}
