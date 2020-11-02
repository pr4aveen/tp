package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.TimeWrapper;

public class DeadlineTest {
    private static final String VALID_TIME = "10:15:30";
    private static final String VALID_DATE = "2019-09-23";

    private static final String VALID_LATER_TIME = "10:15:35";
    private static final String VALID_LATER_DATE = "2019-09-25";
    private static final String INVALID_TIME = "10:86:30";
    private static final String INVALID_DATE = "2019-97-23";
    private static final String INVALID_DEADLINE_DATE = "1900-12-10";

    private static final DateWrapper VALID_CREATED_DATE_WRAPPER = new DateWrapper("2000-01-01");

    private static final Deadline EMPTY_DEADLINE = new Deadline();
    private static final Deadline DEADLINE_WITH_DATE = new Deadline(VALID_DATE, new DateWrapper(VALID_DATE));
    private static final Deadline DEADLINE_WITH_DATE_AND_TIME = new Deadline(VALID_DATE, VALID_TIME,
            VALID_CREATED_DATE_WRAPPER);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null, VALID_CREATED_DATE_WRAPPER));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, null));
        assertThrows(NullPointerException.class, () -> new Deadline(null, null, VALID_CREATED_DATE_WRAPPER));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, null, VALID_CREATED_DATE_WRAPPER));
        assertThrows(NullPointerException.class, () -> new Deadline(null, VALID_TIME, VALID_CREATED_DATE_WRAPPER));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, VALID_TIME, null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Deadline("", VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DATE, INVALID_TIME,
                VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DATE, VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () ->
                new Deadline(INVALID_DEADLINE_DATE, VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DEADLINE_DATE,
                VALID_TIME, VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void isEmpty() {
        assertTrue(EMPTY_DEADLINE.isEmpty());
        assertFalse(DEADLINE_WITH_DATE.isEmpty());
        assertFalse(DEADLINE_WITH_DATE_AND_TIME.isEmpty());
    }

    @Test
    public void hasTime() {
        assertFalse(EMPTY_DEADLINE.hasTime());
        assertFalse(DEADLINE_WITH_DATE.hasTime());
        assertTrue(DEADLINE_WITH_DATE_AND_TIME.hasTime());
    }

    @Test
    public void getFormattedDeadline_formatsCorrectly() {
        assertEquals("No deadline set", EMPTY_DEADLINE.getFormattedDeadline());
        assertEquals(DEADLINE_WITH_DATE.getDate().getFormatted(), DEADLINE_WITH_DATE.getFormattedDeadline());
        String expectedDeadlineStr = DEADLINE_WITH_DATE_AND_TIME.getDate().getFormatted()
                + " " + DEADLINE_WITH_DATE_AND_TIME.getTime().getFormatted();
        assertEquals(expectedDeadlineStr, DEADLINE_WITH_DATE_AND_TIME.getFormattedDeadline());
    }

    @Test
    public void daysToDeadline() {
        Clock.initFixed(new DateTimeWrapper(VALID_DATE + "T01:01:01"));
        assertEquals(2, new Deadline(VALID_LATER_DATE, new DateWrapper(VALID_DATE)).daysToDeadline());
        assertEquals(0, new Deadline(VALID_DATE, VALID_TIME, new DateWrapper(VALID_DATE)).daysToDeadline());
    }

    @Test
    public void toString_formatsCorrectly() {
        assertEquals("", EMPTY_DEADLINE.toString());
        assertEquals(DEADLINE_WITH_DATE.getDate().toString(), DEADLINE_WITH_DATE.toString());
        String expectedDeadlineStr = DEADLINE_WITH_DATE_AND_TIME.getDate().toString()
                + DEADLINE_WITH_DATE_AND_TIME.getTime().toString();
        assertEquals(expectedDeadlineStr, DEADLINE_WITH_DATE_AND_TIME.toString());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(new DateWrapper(VALID_DATE), new TimeWrapper(VALID_TIME)),
                DEADLINE_WITH_DATE_AND_TIME.hashCode());
        assertEquals(Objects.hash(new DateWrapper(VALID_DATE), Optional.empty()), DEADLINE_WITH_DATE.hashCode());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second Deadline has later date -> returns -1
        assertEquals(new Deadline(VALID_DATE, VALID_CREATED_DATE_WRAPPER).compareTo(new Deadline(VALID_LATER_DATE,
                VALID_CREATED_DATE_WRAPPER)), -1);

        // second Deadline has earlier date -> returns 1
        assertEquals(new Deadline(VALID_LATER_DATE, VALID_CREATED_DATE_WRAPPER).compareTo(new Deadline(VALID_DATE,
                VALID_CREATED_DATE_WRAPPER)), 1);

        // first and second Deadline have same date -> returns 0
        assertEquals(new Deadline(VALID_DATE, VALID_CREATED_DATE_WRAPPER).compareTo(new Deadline("2019-09-23",
                VALID_CREATED_DATE_WRAPPER)), 0);

        // second Deadline has same date, later time -> returns -1
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(
                new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE_WRAPPER)), -1);

        // second Deadline has same date, earlier time -> returns 1
        assertEquals(new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(
                new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER)), 1);

        // second Deadline has same date, SAME time -> returns 0
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(
                new Deadline("2019-09-23", "10:15:30", VALID_CREATED_DATE_WRAPPER)), 0);
    }

    @Test
    public void sameDateCompare_returnsCorrectValue() {
        // second Deadline has same date, later time -> returns -1
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(
                new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE_WRAPPER)), -1);

        // second Deadline has same date, earlier time -> returns 1
        assertEquals(new Deadline(VALID_DATE, VALID_LATER_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(
                new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER)), 1);

        // second Deadline has same date, SAME time -> returns 0
        assertEquals(new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(
                new Deadline("2019-09-23", "10:15:30", VALID_CREATED_DATE_WRAPPER)), 0);
    }
}
