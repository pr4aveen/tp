package seedu.momentum.logic.commands;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.getProjectAtIndex;
import static seedu.momentum.logic.commands.ProjectViewCommand.MESSAGE_NOT_PROJECT;
import static seedu.momentum.logic.commands.ProjectViewCommand.MESSAGE_VIEW_PROJECT_SUCCESS;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;

class ProjectViewCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
    }

    @Test
    public void execute_taskViewValidIndex_throwsError() {
        model.viewTasks(getProjectAtIndex(model, INDEX_FIRST));
        expectedModel.commitToHistory();
        assertCommandFailure(new ProjectViewCommand(INDEX_FIRST), model, MESSAGE_NOT_PROJECT);

    }

    @Test
    public void execute_projectView_showsTaskView() {
        Project project = getProjectAtIndex(model, INDEX_FIRST);
        expectedModel.viewTasks(project);
        expectedModel.commitToHistory();
        assertCommandSuccess(new ProjectViewCommand(INDEX_FIRST), model,
            String.format(MESSAGE_VIEW_PROJECT_SUCCESS, project.getName().fullName), expectedModel);
    }

    @Test
    public void execute_projectViewIndexTooLarge_throwsError() {
        assertCommandFailure(
            new ProjectViewCommand(Index.fromOneBased(1000)), model, MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }
}
