package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;

public class ClearTaskCommandTest {
    @Test
    public void execute_clearTasks_success() {
        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        Project modelProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(modelProject);

        Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        Project newProject = expectedProject.clearTasks();
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(new ClearTaskCommand(), model, ClearCommand.MESSAGE_SUCCESS_TASK, expectedModel);
    }

}
