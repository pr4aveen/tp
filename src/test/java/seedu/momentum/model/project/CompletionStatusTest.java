package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {
    private static final CompletionStatus incomplete = new CompletionStatus();
    private static final CompletionStatus completed = incomplete.reverse();

    @Test
    public void isCompleted() {
        assertFalse(incomplete.isCompleted());
        assertTrue(completed.isCompleted());
    }

    @Test
    public void reverse() {
        assertEquals(incomplete.reverse(), completed);
        assertEquals(completed.reverse(), incomplete);
    }
}
