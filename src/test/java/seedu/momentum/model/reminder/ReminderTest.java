package seedu.momentum.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;

public class ReminderTest {
    private static final String VALID_DATE_TIME = "2019-09-23T10:15:30";
    private static final String VALID_LATER_DATE = "2019-09-25T10:15:35";
    private static final String VALID_LATER_TIME = "2019-09-23T16:15:35";
    private static final String INVALID_DATE_TIME = "2019-97-23 10:86:30";

    private static final DateTimeWrapper NOW = new DateTimeWrapper("2010-09-23T10:15:30");

    private static Reminder emptyReminder;
    private static Reminder reminder;
    private static Reminder laterDateReminder;
    private static Reminder laterTimeReminder;

    @BeforeEach
    public void setUp() {
        Clock.initFixed(NOW);
        emptyReminder = new Reminder();
        reminder = new Reminder(VALID_DATE_TIME);
        laterDateReminder = new Reminder(VALID_LATER_DATE);
        laterTimeReminder = new Reminder(VALID_LATER_TIME);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null));
    }

    @Test
    public void constructor_invalidReminder_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reminder(""));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(INVALID_DATE_TIME));
        assertThrows(IllegalArgumentException.class, () -> new Reminder("2000-10-10T12:12:12"));
    }

    @Test
    public void isEmpty() {
        assertTrue(emptyReminder.isEmpty());
        assertFalse(reminder.isEmpty());
    }
    
    @Test
    public void canSchedule() {
        // empty
        assertFalse(emptyReminder.canSchedule());
        
        // expired
        Clock.initFixed(DateTimeWrapper.MAX);
        assertFalse(reminder.canSchedule());
        assertFalse(reminder.updateExpired().canSchedule());
    }

    @Test
    public void getDateTimeWrapper() {
        assertEquals(new DateTimeWrapper(VALID_DATE_TIME), reminder.getDateTimeWrapper());
    }

    @Test
    public void getDateTimeWrapper_emptyReminder_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> emptyReminder.getDateTimeWrapper());
    }

    @Test
    public void remove() {
        assertEquals(emptyReminder, reminder.remove());
    }
    
    @Test
    public void toDate() {
        Instant instant = reminder.getDateTimeWrapper().get().atZone(ZoneId.systemDefault()).toInstant();
        Date expectedDate = Date.from(instant);
        assertEquals(expectedDate, reminder.toDate());
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
        
        Clock.initFixed(DateTimeWrapper.MAX);
        assertEquals(reminder.getDateTimeWrapper().getFormatted() + " (missed)",
                reminder.updateExpired().getFormattedReminder());
    }

    @Test
    public void toString_formatsCorrectly() {
        assertEquals("", emptyReminder.toString());
        assertEquals(reminder.getDateTimeWrapper().toString(), reminder.toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(reminder.equals(reminder));

        // same date -> returns true
        assertTrue(reminder.equals(new Reminder(VALID_DATE_TIME)));

        // different types -> returns false
        assertFalse(reminder.equals("1"));

        // null -> return false
        assertFalse(reminder.equals(null));

        // different date and time -> return false
        assertFalse(reminder.equals(laterDateReminder));
        assertFalse(reminder.equals(laterTimeReminder));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(emptyReminder.hashCode(), new Reminder().hashCode());
        assertEquals(reminder.hashCode(), new Reminder(VALID_DATE_TIME).hashCode());
    }
}
