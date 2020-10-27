package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void execute_newProject_success() {
        Project validProject = new ProjectBuilder().withName("TEST").build();

        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.addTrackedItem(validProject);

        assertCommandSuccess(new AddCommand(validProject), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validProject), expectedModel);
    }

    /**
     * Tests if add command places project in correct order.
     */
    @Test
    public void execute_addCommand_placesProjectInOrder() {
        Project dana = new ProjectBuilder().withName("Dana").build();
        AddCommand addDanaCommand = new AddCommand(dana);

        // alphabetical order -> Dana gets placed in between Carl and Daniel
        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.orderFilteredProjectList(SortType.ALPHA, true, true);
        expectedModel.addTrackedItem(dana);

        assertCommandSuccess(addDanaCommand, model,
                String.format(AddCommand.MESSAGE_SUCCESS, dana), expectedModel);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        TrackedItem trackedItemInList = model.getProjectBook().getTrackedItemList().get(0);
        assertCommandFailure(new AddCommand(trackedItemInList), model, AddCommand.MESSAGE_DUPLICATE_PROJECT);
    }

}
