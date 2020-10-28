package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

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
        expectedModel.commitToHistory();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_nonEmptyProjectBook_success() {
        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel.setVersionedProjectBook(new ProjectBook());
        expectedModel.commitToHistory();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

}
