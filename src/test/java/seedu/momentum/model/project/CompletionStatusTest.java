package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {
    private static final CompletionStatus INCOMPLETE = new CompletionStatus();
    private static final CompletionStatus COMPLETED = INCOMPLETE.reverse();

    @Test
    public void isCompleted() {
        assertFalse(INCOMPLETE.isCompleted());
        assertTrue(COMPLETED.isCompleted());
    }

    @Test
    public void reverse() {
        assertEquals(INCOMPLETE.reverse(), COMPLETED);
        assertEquals(COMPLETED.reverse(), INCOMPLETE);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(COMPLETED.equals(COMPLETED));

        // same completion status -> returns true
        assertTrue(COMPLETED.equals(new CompletionStatus().reverse()));
        assertTrue(INCOMPLETE.equals(new CompletionStatus()));

        // different types -> returns false
        assertFalse(COMPLETED.equals("1"));

        // null -> return false
        assertFalse(COMPLETED.equals(null));

        // different completion status -> return false
        assertFalse(COMPLETED.equals(INCOMPLETE));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(Boolean.hashCode(false), INCOMPLETE.hashCode());
        assertEquals(Boolean.hashCode(true), COMPLETED.hashCode());
    }
}
