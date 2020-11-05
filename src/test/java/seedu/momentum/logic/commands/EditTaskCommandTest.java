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
import static seedu.momentum.logic.commands.CommandTestUtil.showTaskAtIndex;
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
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.EditTrackedItemDescriptorBuilder;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TaskBuilder;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        Task taskToEdit = (Task) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Task editedTask =
                new TaskBuilder(taskToEdit).withCompletionStatus(CompletionStatus.COMPLETED).build();
        EditCommand.EditTrackedItemDescriptor descriptor = new EditTrackedItemDescriptorBuilder(editedTask).build();
        EditCommand editCommand = new EditTaskCommand(INDEX_FIRST, descriptor, parentProject);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        Project newProject = expectedProject.setTask(taskToEdit, editedTask);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        Index indexLastTrackedItem = Index.fromOneBased(model.getDisplayList().size());
        Task lastTrackedItem = (Task) model.getDisplayList().get(indexLastTrackedItem.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTrackedItem);
        Task editedTask = taskInList.withName(VALID_NAME_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditTrackedItemDescriptor descriptor =
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB)
                        .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
                        .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditTaskCommand(indexLastTrackedItem, descriptor, parentProject);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        Project newProject = expectedProject.setTask(lastTrackedItem, editedTask);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        EditCommand editCommand = new EditTaskCommand(INDEX_FIRST, new EditTrackedItemDescriptor(), parentProject);
        TrackedItem editedTrackedItem = model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTrackedItem);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        Project newProject = expectedProject.setTask(expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased()),
                editedTrackedItem);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        showTaskAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemInFilteredList =
                model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        TrackedItem editedTrackedItem =
                new TaskBuilder(trackedItemInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB).build(),
                parentProject);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTrackedItem);

        Model expectedModel = new ModelManager(new ProjectBook(model.getProjectBook()), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        showTaskAtIndex(expectedModel, INDEX_FIRST);
        Project newProject = expectedProject.setTask(trackedItemInFilteredList, editedTrackedItem);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectUnfilteredList_failure() {
        Clock.initFixed(TypicalTimes.DAY);
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        TrackedItem firstTrackedItem = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        EditTrackedItemDescriptor descriptor =
                new EditTrackedItemDescriptorBuilder(firstTrackedItem).build();
        EditCommand editCommand = new EditTaskCommand(INDEX_SECOND, descriptor, parentProject);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
        Clock.reset();
    }

    @Test
    public void execute_duplicateProjectFilteredList_failure() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        showTaskAtIndex(model, INDEX_FIRST);

        // edit project into a duplicate
        TrackedItem trackedItemInList =
                parentProject.getTaskList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTrackedItemDescriptorBuilder(trackedItemInList).build(),
                parentProject);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidProjectIndexUnfilteredList_failure() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        EditTrackedItemDescriptor descriptor =
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditTaskCommand(outOfBoundIndex, descriptor, parentProject);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of project book
     */
    @Test
    public void execute_invalidProjectIndexFilteredList_failure() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        showTaskAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < parentProject.getTaskList().size());

        EditCommand editCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB).build(),
                parentProject);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Project validProject = new ProjectBuilder().build();
        final EditCommand standardCommand = new EditTaskCommand(INDEX_FIRST, DESC_AMY, validProject);

        // same values -> returns true
        EditTrackedItemDescriptor copyDescriptor = new EditTrackedItemDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST, copyDescriptor, validProject);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearProjectCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_FIRST, DESC_BOB)));
    }

}
