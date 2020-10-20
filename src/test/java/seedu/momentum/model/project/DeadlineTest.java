package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Date;

public class DeadlineTest {
    private static final String VALID_TIME = "10:15:30";
    private static final String VALID_DATE = "2019-09-23";

    private static final String VALID_LATER_TIME = "10:15:35";
    private static final String VALID_LATER_DATE = "2019-09-25";
    private static final String INVALID_TIME = "10:86:30";
    private static final String INVALID_DATE = "2019-97-23";
    private static final String INVALID_DEADLINE_DATE = "1900-12-10";

    private static final Date VALID_CREATED_DATE = new Date("2000-01-01");

    private static final Deadline emptyDeadline = new Deadline();
    private static final Deadline deadlineWithDate = new Deadline(VALID_DATE, new Date(VALID_DATE));
    private static final Deadline deadlineWithDateAndTime = new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null, VALID_CREATED_DATE));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, null));
        assertThrows(NullPointerException.class, () -> new Deadline(null, null, VALID_CREATED_DATE));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, null, VALID_CREATED_DATE));
        assertThrows(NullPointerException.class, () -> new Deadline(null, VALID_TIME, VALID_CREATED_DATE));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, VALID_TIME, null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Deadline("", VALID_CREATED_DATE));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DATE, INVALID_TIME,
                VALID_CREATED_DATE));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DATE, VALID_CREATED_DATE));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DEADLINE_DATE, VALID_CREATED_DATE));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DEADLINE_DATE,
                VALID_TIME, VALID_CREATED_DATE));
    }

    @Test
    public void isEmpty() {
        assertTrue(emptyDeadline.isEmpty());
        assertFalse(deadlineWithDate.isEmpty());
        assertFalse(deadlineWithDateAndTime.isEmpty());
    }

    @Test
    public void hasTime() {
        assertFalse(emptyDeadline.hasTime());
        assertFalse(deadlineWithDate.hasTime());
        assertTrue(deadlineWithDateAndTime.hasTime());
    }

    @Test
    public void getFormattedDeadline_formatsCorrectly() {
        assertEquals("No deadline set", emptyDeadline.getFormattedDeadline());
        assertEquals(deadlineWithDate.getDate().getFormatted(), deadlineWithDate.getFormattedDeadline());
        String expectedDeadlineStr = deadlineWithDateAndTime.getDate().getFormatted()
                + " " + deadlineWithDateAndTime.getTime().getFormatted();
        assertEquals(expectedDeadlineStr, deadlineWithDateAndTime.getFormattedDeadline());
    }

    @Test
    public void toString_formatsCorrectly() {
        assertEquals("", emptyDeadline.toString());
        assertEquals(deadlineWithDate.getDate().toString(), deadlineWithDate.toString());
        String expectedDeadlineStr = deadlineWithDateAndTime.getDate().toString()
                + deadlineWithDateAndTime.getTime().toString();
        assertEquals(expectedDeadlineStr, deadlineWithDateAndTime.toString());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second Deadline has later date -> returns -1
        assertEquals(new Deadline(VALID_DATE, VALID_CREATED_DATE).compareTo(new Deadline(VALID_LATER_DATE,
                VALID_CREATED_DATE)), -1);

        // second Deadline has earlier date -> returns 1
        assertEquals(new Deadline(VALID_LATER_DATE, VALID_CREATED_DATE).compareTo(new Deadline(VALID_DATE,
                VALID_CREATED_DATE)), 1);

        // first and second Deadline have same date -> returns 0
        assertEquals(new Deadline(VALID_DATE, VALID_CREATED_DATE).compareTo(new Deadline("2019-09-23",
                VALID_CREATED_DATE)), 0);

        // second Deadline has same date, later time -> returns -1
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE).compareTo(
                new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE)), -1);

        // second Deadline has same date, earlier time -> returns 1
        assertEquals(new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE).compareTo(
                new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE)), 1);

        // second Deadline has same date, SAME time -> returns 0
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE).compareTo(
                new Deadline("2019-09-23", "10:15:30", VALID_CREATED_DATE)), 0);
    }

    @Test
    public void sameDateCompare_returnsCorrectValue() {
        // second Deadline has same date, later time -> returns -1
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE).compareTo(
                new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE)), -1);

        // second Deadline has same date, earlier time -> returns 1
        assertEquals(new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE).compareTo(
                new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE)), 1);

        // second Deadline has same date, SAME time -> returns 0
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE).compareTo(
                new Deadline("2019-09-23", "10:15:30", VALID_CREATED_DATE)), 0);
    }
}
