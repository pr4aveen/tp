package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.momentum.logic.commands.ListCommand.MESSAGE_SUCCESS_PROJECTS;
import static seedu.momentum.logic.commands.ListCommand.MESSAGE_SUCCESS_TASKS;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
    }

    @Test
    public void execute_projectListIsNotFiltered_showsSameList() {
        expectedModel.commitToHistory();
        assertCommandSuccess(new ListCommand(), model, MESSAGE_SUCCESS_PROJECTS, expectedModel);
    }

    @Test
    public void execute_projectListIsFiltered_showsEverything() {
        showProjectAtIndex(model, INDEX_FIRST);
        expectedModel.commitToHistory();
        assertCommandSuccess(new ListCommand(), model, MESSAGE_SUCCESS_PROJECTS, expectedModel);
    }

    @Test
    public void execute_taskListIsNotFiltered_showsSameList() {
        model.viewTasks(ALICE);
        expectedModel.viewTasks(ALICE);
        System.out.println(model.getDisplayList());
        expectedModel.commitToHistory();
        assertCommandSuccess(new ListCommand(), model,
            String.format(MESSAGE_SUCCESS_TASKS, ALICE.getName().fullName), expectedModel);
    }

    @Test
    public void execute_taskListIsFiltered_showsEverything() {
        model.viewTasks(ALICE);
        showTaskAtIndex(model, INDEX_FIRST);
        expectedModel.viewTasks(ALICE);
        expectedModel.commitToHistory();
        assertCommandSuccess(new ListCommand(), model,
                String.format(MESSAGE_SUCCESS_TASKS, ALICE.getName().fullName), expectedModel);
    }
}
