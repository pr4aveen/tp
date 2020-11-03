package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValid() {
        assertTrue(new Description("").isEmpty());
        assertTrue(new Description("    ").isEmpty());
        assertFalse(new Description("1").isEmpty());
    }

    @Test
    public void equals() {
        Description description = new Description("description");
        // same object -> returns true
        assertTrue(description.equals(description));

        // same description -> returns true
        assertTrue(description.equals(new Description("description")));

        // different types -> returns false
        assertFalse(description.equals("1"));

        // null -> return false
        assertFalse(description.equals(null));

        // different name -> return false
        assertFalse(description.equals("descriptionfsd"));
    }

}
