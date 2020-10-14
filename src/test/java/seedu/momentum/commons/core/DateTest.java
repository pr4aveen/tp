package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.DateUtil;

public class DateTest {
    private static final String VALID_DATE = "2019-09-23";
    private static final String VALID_LATER_DATE = "2019-09-25";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((String) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "asfd";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date time
        assertThrows(NullPointerException.class, () -> Date.isValid(null));

        // invalid date time
        assertFalse(Date.isValid("")); // empty string
        assertFalse(Date.isValid(" ")); // spaces only
        assertFalse(Date.isValid("^")); // only non-alphanumeric characters
        assertFalse(Date.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(Date.isValid("12/02/12*")); // wrong format
        assertFalse(Date.isValid("2019-30-23")); // not a real date

        // valid date time
        assertTrue(Date.isValid(VALID_DATE)); // typical time
    }

    @Test
    public void getFormattedDate_formatsCorrectly() {
        Date date = new Date(VALID_DATE);
        assertEquals(date.get().format(DateUtil.FORMAT_DATE_MEDIUM), date.getFormatted());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second Date is later
        assertEquals(new Date(VALID_DATE).compareTo(new Date(VALID_LATER_DATE)), -1);

        // second Date is earlier
        assertEquals(new Date(VALID_LATER_DATE).compareTo(new Date(VALID_DATE)), 1);

        // both Date same date
        assertEquals(new Date(VALID_DATE).compareTo(new Date("2019-09-23")), 0);
    }
}
