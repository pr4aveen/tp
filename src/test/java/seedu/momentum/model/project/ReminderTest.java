package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.model.reminder.Reminder;

public class ReminderTest {
    private static final String VALID_DATE_TIME = "2019-09-23T10:15:30";
    private static final String VALID_LATER_DATE = "2019-09-25T10:15:35";
    private static final String VALID_LATER_TIME = "2019-09-23T16:15:35";
    private static final String INVALID_DATE_TIME = "2019-97-23 10:86:30";

    private static final DateWrapper VALID_CREATED_DATE_WRAPPER = new DateWrapper("2000-01-01");

    private static final Reminder emptyReminder = new Reminder();
    private static final Reminder reminder = new Reminder(VALID_DATE_TIME, VALID_CREATED_DATE_WRAPPER);
    private static final Reminder laterDateReminder = new Reminder(VALID_LATER_DATE, VALID_CREATED_DATE_WRAPPER);
    private static final Reminder laterTimeReminder = new Reminder(VALID_LATER_TIME, VALID_CREATED_DATE_WRAPPER);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void constructor_invalidReminder_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reminder("", VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(INVALID_DATE_TIME, VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () ->
                new Reminder(VALID_DATE_TIME, new DateWrapper(VALID_LATER_DATE)));
    }

    @Test
    public void isEmpty() {
        assertTrue(emptyReminder.isEmpty());
        assertFalse(reminder.isEmpty());
    }

    @Test
    public void getDateTimeWrapper() {
        assertEquals(new DateTimeWrapper(VALID_DATE_TIME), reminder.getDateTimeWrapper());
    }

    @Test
    public void getDateTimeWrapper_emptyReminder() {
        assertThrows(NoSuchElementException.class, () -> emptyReminder.getDateTimeWrapper());
    }

    @Test
    public void getStatus() {
        assertEquals("", emptyReminder.getStatus());
        assertEquals("\ud83d\udd14", reminder.getStatus());
    }

    @Test
    public void getFormattedReminder_formatsCorrectly() {
        assertEquals("No reminder set", emptyReminder.getFormattedReminder());
        assertEquals(reminder.getDateTimeWrapper().getFormatted(), reminder.getFormattedReminder());
    }

    @Test
    public void toString_formatsCorrectly() {
        assertEquals("", emptyReminder.toString());
        assertEquals(reminder.getDateTimeWrapper().toString(), reminder.toString());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // both reminders are empty -> returns 0
        assertEquals(emptyReminder.compareTo(emptyReminder), 0);

        // first reminder is empty -> returns 1
        assertEquals(emptyReminder.compareTo(reminder), 1);

        // second reminder is empty -> returns -1
        assertEquals(reminder.compareTo(emptyReminder), -1);

        // second reminder has later date -> returns -1
        assertEquals(reminder.compareTo(laterDateReminder), -1);

        // second reminder has earlier date -> returns 1
        assertEquals(laterDateReminder.compareTo(reminder), 1);

        // first and second reminder have same date and time -> returns 0
        assertEquals(new Reminder(VALID_DATE_TIME, VALID_CREATED_DATE_WRAPPER).compareTo(reminder), 0);

        // second reminder has same date, later time -> returns -1
        assertEquals(reminder.compareTo(laterTimeReminder), -1);

        // second reminder has same date, earlier time -> returns 1
        assertEquals(laterDateReminder.compareTo(reminder), 1);
    }
}
