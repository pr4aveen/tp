package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    private static final String VALID_TIME = "10:15:30";
    private static final String VALID_DATE = "2019-09-23";
    private static final String INVALID_TIME = "10:86:30";
    private static final String INVALID_DATE = "2019-97-23";

    private static final Deadline emptyDeadline = new Deadline();
    private static final Deadline deadlineWithDate = new Deadline(VALID_DATE);
    private static final Deadline deadlineWithDateAndTime = new Deadline(VALID_DATE, VALID_TIME);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
        assertThrows(NullPointerException.class, () -> new Deadline(null, null));
        assertThrows(NullPointerException.class, () -> new Deadline(VALID_DATE, null));
        assertThrows(NullPointerException.class, () -> new Deadline(null, VALID_TIME));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Deadline(""));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(INVALID_DATE, INVALID_TIME));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(VALID_TIME));
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
        assertEquals("", emptyDeadline.getFormattedDeadline());
        assertEquals(deadlineWithDate.getDate().getFormattedDate(), deadlineWithDate.getFormattedDeadline());
        String expectedDeadlineStr = deadlineWithDateAndTime.getDate().getFormattedDate()
                + " " + deadlineWithDateAndTime.getTime().getFormattedTime();
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
}
