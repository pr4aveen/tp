package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.EditCommand.EditTrackedItemDescriptor;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.EditTrackedItemDescriptorBuilder;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditProjectCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Project editedProject =
            new ProjectBuilder(model.getDisplayList().get(0)).withCompletionStatus(CompletionStatus.COMPLETED).build();
        EditCommand.EditTrackedItemDescriptor descriptor = new EditTrackedItemDescriptorBuilder(editedProject).build();
        EditCommand editCommand = new EditProjectCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        expectedModel.setTrackedItem(model.getDisplayList().get(0), editedProject);
        expectedModel.commitToHistory();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTrackedItem = Index.fromOneBased(model.getDisplayList().size());
        TrackedItem lastTrackedItem = model.getDisplayList().get(indexLastTrackedItem.getZeroBased());

        ProjectBuilder projectInList = new ProjectBuilder(lastTrackedItem);
        Project editedProject = projectInList.withName(VALID_NAME_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditTrackedItemDescriptor descriptor =
            new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditProjectCommand(indexLastTrackedItem, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        expectedModel.setTrackedItem(lastTrackedItem, editedProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditProjectCommand(INDEX_FIRST, new EditTrackedItemDescriptor());
        TrackedItem editedTrackedItem = model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedTrackedItem);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        expectedModel.setTrackedItem(editedTrackedItem, editedTrackedItem);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemInFilteredList =
            model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        TrackedItem editedTrackedItem = new ProjectBuilder(trackedItemInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditProjectCommand(INDEX_FIRST,
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROJECT_SUCCESS, editedTrackedItem);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        showProjectAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.setTrackedItem(model.getDisplayList().get(0), editedTrackedItem);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectUnfilteredList_failure() {
        Clock.initFixed(TypicalTimes.DAY);
        TrackedItem firstTrackedItem = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        EditTrackedItemDescriptor descriptor =
                new EditTrackedItemDescriptorBuilder(firstTrackedItem).withCompletionStatus(null).build();
        EditCommand editCommand = new EditProjectCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PROJECT);
        Clock.reset();
    }

    @Test
    public void execute_duplicateProjectFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST);

        // edit project in filtered list into a duplicate in project book
        TrackedItem trackedItemInList =
            model.getProjectBook().getTrackedItemList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditProjectCommand(INDEX_FIRST,
                new EditTrackedItemDescriptorBuilder(trackedItemInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_invalidProjectIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        EditTrackedItemDescriptor descriptor =
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of project book
     */
    @Test
    public void execute_invalidProjectIndexFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProjectBook().getTrackedItemList().size());

        EditCommand editCommand = new EditProjectCommand(outOfBoundIndex,
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditProjectCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditTrackedItemDescriptor copyDescriptor = new EditTrackedItemDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditProjectCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_FIRST, DESC_BOB)));
    }

}
