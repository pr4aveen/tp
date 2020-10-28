package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.util.DateUtil;

public class DateWrapperTest {
    private static final String VALID_DATE = "2019-09-23";
    private static final String VALID_LATER_DATE = "2019-09-25";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateWrapper((String) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "asfd";
        assertThrows(IllegalArgumentException.class, () -> new DateWrapper(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date time
        assertThrows(NullPointerException.class, () -> DateWrapper.isValid(null));

        // invalid date time
        assertFalse(DateWrapper.isValid("")); // empty string
        assertFalse(DateWrapper.isValid(" ")); // spaces only
        assertFalse(DateWrapper.isValid("^")); // only non-alphanumeric characters
        assertFalse(DateWrapper.isValid("peter*")); // contains non-alphanumeric characters
        assertFalse(DateWrapper.isValid("12/02/12*")); // wrong format
        assertFalse(DateWrapper.isValid("2019-30-23")); // not a real date

        // valid date time
        assertTrue(DateWrapper.isValid(VALID_DATE)); // typical time
    }

    @Test
    public void getFormattedDate_formatsCorrectly() {
        DateWrapper dateWrapper = new DateWrapper(VALID_DATE);
        assertEquals(dateWrapper.get().format(DateUtil.FORMAT_DATE_MEDIUM), dateWrapper.getFormatted());
    }

    @Test
    public void compareTo_returnsCorrectValue() {
        // second Date is later
        assertEquals(new DateWrapper(VALID_DATE).compareTo(new DateWrapper(VALID_LATER_DATE)), -1);

        // second Date is earlier
        assertEquals(new DateWrapper(VALID_LATER_DATE).compareTo(new DateWrapper(VALID_DATE)), 1);

        // both Date same date
        assertEquals(new DateWrapper(VALID_DATE).compareTo(new DateWrapper("2019-09-23")), 0);
    }
}
