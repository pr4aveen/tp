package seedu.momentum.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Time;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.Deadline;

public class JsonAdaptedDeadlineTest {
    private static final String INVALID_DATE = "2020-42-99";
    private static final String INVALID_TIME = "65:21:02";
    private static final String VALID_DATE = "2020-02-09";
    private static final String VALID_TIME = "05:21:02";
    private static final Date VALID_CREATED_DATE = new Date("2000-02-09");

    @Test
    public void toModelType_validDeadlineDetails_returnsDeadline() throws Exception {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_DATE, null);
        Deadline expectedDeadline = new Deadline(VALID_DATE, VALID_CREATED_DATE);
        assertEquals(deadline.toModelType(VALID_CREATED_DATE), expectedDeadline);

        deadline = new JsonAdaptedDeadline(VALID_DATE, VALID_TIME);
        expectedDeadline = new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE);
        assertEquals(deadline.toModelType(VALID_CREATED_DATE), expectedDeadline);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        final JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(INVALID_DATE, null);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> deadline.toModelType(VALID_CREATED_DATE));

        final JsonAdaptedDeadline deadline2 = new JsonAdaptedDeadline(VALID_DATE, INVALID_TIME);
        expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> deadline2.toModelType(VALID_CREATED_DATE));

        final JsonAdaptedDeadline deadline3 = new JsonAdaptedDeadline(INVALID_DATE, INVALID_TIME);
        expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> deadline3.toModelType(VALID_CREATED_DATE));
    }

}
