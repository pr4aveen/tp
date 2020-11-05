package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {

    private static final String FEEDBACK = "feedback";
    private static final String DIFFERENT = "different";

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult(FEEDBACK);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult(FEEDBACK)));
        assertTrue(commandResult.equals(new CommandResult(FEEDBACK, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult(DIFFERENT)));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult(FEEDBACK, true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult(FEEDBACK, false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult(FEEDBACK);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult(FEEDBACK).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(DIFFERENT).hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(DIFFERENT, true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(FEEDBACK, false, true).hashCode());
    }

    @Test
    public void isExit_returnsCorrectValue() {
        // show help and exit false
        CommandResult commandResult = new CommandResult(FEEDBACK);
        assertEquals(commandResult.isExit(), false);

        // show help and exist false (overloaded constructor)
        commandResult = new CommandResult(FEEDBACK, false, false);
        assertEquals(commandResult.isExit(), false);

        // show help true, exit false
        commandResult = new CommandResult(FEEDBACK, true, false);
        assertEquals(commandResult.isExit(), false);

        // show help false, exit true
        commandResult = new CommandResult(FEEDBACK, false, true);
        assertEquals(commandResult.isExit(), true);

        // both show help and exit true
        commandResult = new CommandResult(FEEDBACK, true, true);
        assertEquals(commandResult.isExit(), true);
    }

    @Test
    public void isHelp_returnsCorrectValue() {
        // show help and exit false
        CommandResult commandResult = new CommandResult(FEEDBACK);
        assertEquals(commandResult.isShowHelp(), false);

        // show help and exist false (overloaded constructor)
        commandResult = new CommandResult(FEEDBACK, false, false);
        assertEquals(commandResult.isShowHelp(), false);

        // show help true, exit false
        commandResult = new CommandResult(FEEDBACK, true, false);
        assertEquals(commandResult.isShowHelp(), true);

        // show help false, exit true
        commandResult = new CommandResult(FEEDBACK, false, true);
        assertEquals(commandResult.isShowHelp(), false);

        // both show help and exit true
        commandResult = new CommandResult(FEEDBACK, true, true);
        assertEquals(commandResult.isShowHelp(), true);
    }
}
