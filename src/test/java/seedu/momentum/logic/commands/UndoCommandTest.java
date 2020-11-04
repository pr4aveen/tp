package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

/**
 * Contains integration tests with the Model and other commands.
 */
public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_cannotUndoCommand_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_CANNOT_UNDO);
    }

//    @Test
//    public void execute_canUndoCommand_success() {
//
//        UndoCommand undoCommand = new UndoCommand();
//
//        // AddCommand for Project
//        Project validProject = new ProjectBuilder().withName("TEST").build();
//
//        Model afterModel = new ModelManager(model.getProjectBook(), new UserPrefs());
//        afterModel.addTrackedItem(validProject);
//        afterModel.commitToHistory();
//
//        Model expectedBeforeModel = afterModel.deleteTrackedItem(validProject);
//        expectedBeforeModel.commitToHistory();
//
//        assertCommandSuccess(undoCommand, afterModel, UndoCommand.MESSAGE_SUCCESS, beforeModel);
//
//    }

}
