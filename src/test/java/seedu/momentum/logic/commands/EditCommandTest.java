package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.EditCommand.EditProjectDescriptor;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.EditProjectDescriptorBuilder;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Project editedProject = new ProjectBuilder().build();
        EditCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(editedProject).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PROJECT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProject = Index.fromOneBased(model.getFilteredProjectList().size());
        Project lastProject = model.getFilteredProjectList().get(indexLastProject.getZeroBased());

        ProjectBuilder projectInList = new ProjectBuilder(lastProject);
        Project editedProject = projectInList.withName(VALID_NAME_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastProject, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        expectedModel.setProject(lastProject, editedProject);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PROJECT, new EditProjectDescriptor());
        Project editedProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project projectInFilteredList = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(projectInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PROJECT,
                new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectUnfilteredList_failure() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(firstProject).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PROJECT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_duplicateProjectFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        // edit project in filtered list into a duplicate in project book
        Project projectInList = model.getProjectBook().getProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PROJECT,
                new EditProjectDescriptorBuilder(projectInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_invalidProjectIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        EditCommand.EditProjectDescriptor descriptor =
                new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of project book
     */
    @Test
    public void execute_invalidProjectIndexFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);
        Index outOfBoundIndex = INDEX_SECOND_PROJECT;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProjectBook().getProjectList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PROJECT, DESC_AMY);

        // same values -> returns true
        EditProjectDescriptor copyDescriptor = new EditCommand.EditProjectDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PROJECT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PROJECT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PROJECT, DESC_BOB)));
    }

}
