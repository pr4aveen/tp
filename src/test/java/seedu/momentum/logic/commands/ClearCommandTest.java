package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalPersons.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyProjectBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyProjectBook_success() {
        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel.setProjectBook(new ProjectBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
