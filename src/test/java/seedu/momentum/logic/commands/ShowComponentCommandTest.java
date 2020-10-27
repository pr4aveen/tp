package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.parser.ShowComponentCommandParser;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

public class ShowComponentCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowComponentCommand(null));
    }

    @Test
    public void execute_dismissReminderComponent_successful() {
        ShowComponentCommand showComponentCommand =
                new ShowComponentCommand(ShowComponentCommandParser.ComponentType.REMINDER);
        String expectedMessage = String.format(ShowComponentCommand.MESSAGE_SUCCESS,
                ShowComponentCommandParser.ComponentType.REMINDER.toString(),
                ShowComponentCommand.REMOVED);
        assertCommandSuccess(showComponentCommand, model, expectedMessage, model);
    }
}
