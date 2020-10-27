package seedu.momentum.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;

public class ReminderTest {
    private static final String VALID_DATE_TIME = "2019-09-23T10:15:30";
    private static final String VALID_LATER_DATE = "2019-09-25T10:15:35";
    private static final String VALID_LATER_TIME = "2019-09-23T16:15:35";
    private static final String INVALID_DATE_TIME = "2019-97-23 10:86:30";

    private static final DateWrapper VALID_CREATED_DATE_WRAPPER = new DateWrapper("2000-01-01");
    private static final DateTimeWrapper NOW = new DateTimeWrapper("2010-09-23T10:15:30");

    private static Reminder emptyReminder;
    private static Reminder reminder;
    private static Reminder laterDateReminder;
    private static Reminder laterTimeReminder;

    private void init() {
        Clock.initFixed(NOW);
        emptyReminder = new Reminder();
        reminder = new Reminder(VALID_DATE_TIME, VALID_CREATED_DATE_WRAPPER);
        laterDateReminder = new Reminder(VALID_LATER_DATE, VALID_CREATED_DATE_WRAPPER);
        laterTimeReminder = new Reminder(VALID_LATER_TIME, VALID_CREATED_DATE_WRAPPER);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        init();
        assertThrows(NullPointerException.class, () -> new Reminder(null, VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void constructor_invalidReminder_throwsIllegalArgumentException() {
        init();
        assertThrows(IllegalArgumentException.class, () -> new Reminder("", VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(INVALID_DATE_TIME, VALID_CREATED_DATE_WRAPPER));
        assertThrows(IllegalArgumentException.class, () ->
                new Reminder(VALID_DATE_TIME, new DateWrapper(VALID_LATER_DATE)));
        assertThrows(IllegalArgumentException.class, () ->
                new Reminder("2000-10-10T12:12:12", VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void isEmpty() {
        init();
        assertTrue(emptyReminder.isEmpty());
        assertFalse(reminder.isEmpty());
    }

    @Test
    public void getDateTimeWrapper() {
        init();
        assertEquals(new DateTimeWrapper(VALID_DATE_TIME), reminder.getDateTimeWrapper());
    }

    @Test
    public void getDateTimeWrapper_emptyReminder_throwsNoSuchElementException() {
        init();
        assertThrows(NoSuchElementException.class, () -> emptyReminder.getDateTimeWrapper());
    }

    @Test
    public void getStatus() {
        init();
        assertEquals("", emptyReminder.getStatus());
        assertEquals("\ud83d\udd14", reminder.getStatus());
    }

    @Test
    public void getFormattedReminder_formatsCorrectly() {
        init();
        assertEquals("No reminder set", emptyReminder.getFormattedReminder());
        assertEquals(reminder.getDateTimeWrapper().getFormatted(), reminder.getFormattedReminder());
    }

    @Test
    public void remove() {
        init();
        assertEquals(emptyReminder, reminder.remove());
    }

    @Test
    public void toDate() {
        init();
        Instant instant = reminder.getDateTimeWrapper().get().atZone(ZoneId.systemDefault()).toInstant();
        Date expectedDate = Date.from(instant);
        assertEquals(expectedDate, reminder.toDate());
    }

    @Test
    public void toString_formatsCorrectly() {
        init();
        assertEquals("", emptyReminder.toString());
        assertEquals(reminder.getDateTimeWrapper().toString(), reminder.toString());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        init();

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
