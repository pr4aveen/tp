package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.getProjectAtIndex;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

class HomeCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
    }

    @Test
    public void execute_taskView_showsProjectList() {
        model.viewTasks(getProjectAtIndex(model, Index.fromZeroBased(0)));
        expectedModel.commitToHistory();
        assertCommandSuccess(new HomeCommand(), model, HomeCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, expectedModel);
    }

    @Test
    public void execute_projectView_showsProjectList() {
        expectedModel.commitToHistory();
        assertCommandSuccess(new HomeCommand(), model, HomeCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, expectedModel);
    }
}
