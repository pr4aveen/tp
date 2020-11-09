package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.VALID_STATISTIC_TIMEFRAME_DAILY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.logic.parser.FindCommandParser.FIND_ARGUMENT_DELIMITER;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.ThreadWrapper;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.ShowComponentCommandParser;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.EditTrackedItemDescriptorBuilder;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.SettingsToChangeBuilder;
import seedu.momentum.testutil.TaskBuilder;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains unit tests for {@code UndoCommand}.
 */
public class UndoCommandTest {

    private static final Index INDEX_TEST_PROJECT = Index.fromZeroBased(4);
    private static final UndoCommand UNDO_COMMAND = new UndoCommand();

    private Model expectedModel;
    private Model model;
    private Project testProjectWithTask;
    private Task testTask;

    @BeforeEach
    public void setUp() {
        Project testProjectNoTask = new ProjectBuilder().withName("TEST").build();
        testTask = new TaskBuilder().withName("TEST").build();
        testProjectWithTask = testProjectNoTask.addTask(testTask);

        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        model.addTrackedItem(testProjectNoTask);
        model.setTrackedItem(testProjectNoTask, testProjectWithTask);
        model.commitToHistory();

        expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel.addTrackedItem(testProjectNoTask);
        expectedModel.setTrackedItem(testProjectNoTask, testProjectWithTask);
        expectedModel.commitToHistory();
    }

    @Test
    public void execute_noCommandHistoryToUndo_throwsCommandException() {
        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

        assertCommandFailure(UNDO_COMMAND, model, UndoCommand.MESSAGE_CANNOT_UNDO);
    }

    /**
     * help command is not committed in history log and cannot be undone.
     * since redo is only applicable to undone commands, there is no redo test for help command.
     */
    @Test
    public void execute_undoHelpCommand_throwsCommandException() {

        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        HelpCommand helpCommand = new HelpCommand();

        helpCommand.execute(model);
        helpCommand.execute(expectedModel);

        assertCommandFailure(UNDO_COMMAND, model, UndoCommand.MESSAGE_CANNOT_UNDO);
    }

    @Test
    public void execute_undoAddCommand_success() {

        // undo the adding of a new project
        Project testProjectTwo = new ProjectBuilder().withName("TEST TWO").build();
        AddCommand addProjectCommand = new AddProjectCommand(testProjectTwo);

        try {
            addProjectCommand.execute(model);
            addProjectCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // undo the adding of a new task
        Task testTaskTwo = new TaskBuilder().withName("TEST TWO").build();
        AddCommand addTaskCommand = new AddTaskCommand(testTaskTwo, testProjectTwo);

        try {
            addProjectCommand.execute(model);
            addTaskCommand.execute(model);
            addProjectCommand.execute(expectedModel);
            addTaskCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoClearCommand_success() {

        // undo the clearing of projects
        ClearProjectCommand clearProjectCommand = new ClearProjectCommand();

        try {
            clearProjectCommand.execute(model);
            clearProjectCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // undo the clearing of tasks within a project
        ClearTaskCommand clearTaskCommand = new ClearTaskCommand();

        model.viewTasks(testProjectWithTask);
        expectedModel.viewTasks(testProjectWithTask);

        try {
            clearTaskCommand.execute(model);
            clearTaskCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoDeleteCommand_success() {

        // undo the deleting of projects
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(INDEX_FIRST);

        try {
            deleteProjectCommand.execute(model);
            deleteProjectCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // undo the deleting of tasks within a project
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST, testProjectWithTask);

        model.viewTasks(testProjectWithTask);
        expectedModel.viewTasks(testProjectWithTask);

        try {
            deleteTaskCommand.execute(model);
            deleteTaskCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoDismissCommand_success() {
        Clock.reset();
        ThreadWrapper.setIsRunningOnPlatform(false);

        String dateTimeStr = Clock.now().plus(500, ChronoUnit.MILLIS).toString();
        Project testProjectTwo = new ProjectBuilder().withName("TEST TWO").withReminder(dateTimeStr).build();
        Project testProjectThree = new ProjectBuilder().withName("TEST TWO").withReminder(dateTimeStr).build();
        DismissCommand dismissCommand = new DismissCommand();

        model.addTrackedItem(testProjectTwo);
        model.commitToHistory();
        expectedModel.addTrackedItem(testProjectThree);
        expectedModel.commitToHistory();

        try {
            Thread threadModel = new Thread(() -> {
                try {
                    dismissCommand.execute(model);
                    dismissCommand.execute(expectedModel);

                    expectedModel.undoCommand();

                    assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
                } catch (CommandException e) {
                    throw new AssertionError("This should not throw an exception.");
                }
            });
            Thread.sleep(1000);
            threadModel.start();
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

    }

    @Test
    public void execute_undoEditCommand_success() {

        // undo the editing of projects
        Project editedProject = new ProjectBuilder(model.getDisplayList().get(0))
                .withCompletionStatus(CompletionStatus.COMPLETED).build();
        EditCommand.EditTrackedItemDescriptor descriptor = new EditTrackedItemDescriptorBuilder(editedProject).build();
        EditProjectCommand editProjectCommand = new EditProjectCommand(INDEX_FIRST, descriptor);

        try {
            editProjectCommand.execute(model);
            editProjectCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // undo the editing of tasks within a project
        TaskBuilder taskBuilder = new TaskBuilder(testTask);
        Task editedTask = taskBuilder.withName("TEST EDIT").build();
        descriptor = new EditTrackedItemDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST, descriptor, testProjectWithTask);

        model.viewTasks(testProjectWithTask);
        expectedModel.viewTasks(testProjectWithTask);

        try {
            editTaskCommand.execute(model);
            editTaskCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoFindCommand_success() {

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(FindType.ANY,
                Arrays.asList("TEST".split(FIND_ARGUMENT_DELIMITER)));
        FindCommand findCommand = new FindCommand(predicate);

        try {
            findCommand.execute(model);
            findCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoHomeCommand_success() {

        HomeCommand homeCommand = new HomeCommand();

        model.viewTasks(testProjectWithTask);
        expectedModel.viewTasks(testProjectWithTask);

        try {
            homeCommand.execute(model);
            homeCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoListCommand_success() {

        ListCommand listCommand = new ListCommand();

        showProjectAtIndex(model, INDEX_FIRST);
        showProjectAtIndex(expectedModel, INDEX_FIRST);

        try {
            listCommand.execute(model);
            listCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoProjectViewCommand_success() {

        ProjectViewCommand projectViewCommand = new ProjectViewCommand(INDEX_FIRST);

        try {
            projectViewCommand.execute(model);
            projectViewCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoSetCommand_success() {

        SetCommand.SettingsToChange settingsToChange = new SettingsToChangeBuilder().withTheme(VALID_THEME_LIGHT)
                .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_DAILY).build();
        SetCommand setCommand = new SetCommand(settingsToChange);

        try {
            setCommand.execute(model);
            setCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoShowComponentCommand_success() {

        ShowComponentCommand showComponentCommand =
                new ShowComponentCommand(ShowComponentCommandParser.ComponentType.TAGS);

        try {
            showComponentCommand.execute(model);
            showComponentCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoSortComponentCommand_success() {

        SortCommand sortCommand = ALPHA_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;

        try {
            sortCommand.execute(model);
            sortCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoStartCommand_success() {

        // undo starting of timer for project
        StartProjectCommand startProjectCommand = new StartProjectCommand(INDEX_FIRST);

        try {
            startProjectCommand.execute(model);
            startProjectCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // undo starting of timer for task in a project
        StartTaskCommand startTaskCommand = new StartTaskCommand(INDEX_FIRST, testProjectWithTask);

        model.viewTasks(testProjectWithTask);
        expectedModel.viewTasks(testProjectWithTask);

        try {
            startTaskCommand.execute(model);
            startTaskCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoStopCommand_success() {

        // undo stopping of timer for project
        Clock.initManual(TypicalTimes.DAY);
        Project testProjectAfterTimerIsStarted = testProjectWithTask.startTimer();
        StopProjectCommand stopProjectCommand = new StopProjectCommand(INDEX_TEST_PROJECT);

        model.setTrackedItem(testProjectWithTask, testProjectAfterTimerIsStarted);
        model.commitToHistory();
        expectedModel.setTrackedItem(testProjectWithTask, testProjectAfterTimerIsStarted);
        expectedModel.commitToHistory();

        Clock.advance(1, ChronoUnit.HOURS);

        try {
            stopProjectCommand.execute(model);
            stopProjectCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        Clock.reset();

        // undo stopping of timer for task in a project
        Clock.initManual(TypicalTimes.DAY);
        Task testTaskAfterTimerIsStarted = testTask.startTimer();
        Project projectAfterTimerForTaskIsStarted = testProjectWithTask.setTask(testTask, testTaskAfterTimerIsStarted);
        StopTaskCommand stopTaskCommand = new StopTaskCommand(INDEX_FIRST, projectAfterTimerForTaskIsStarted);

        model.setTrackedItem(testProjectWithTask, projectAfterTimerForTaskIsStarted);
        model.viewTasks(projectAfterTimerForTaskIsStarted);
        model.commitToHistory();
        expectedModel.setTrackedItem(testProjectWithTask, projectAfterTimerForTaskIsStarted);
        expectedModel.viewTasks(projectAfterTimerForTaskIsStarted);
        expectedModel.commitToHistory();

        Clock.advance(1, ChronoUnit.HOURS);

        try {
            stopTaskCommand.execute(model);
            stopTaskCommand.execute(expectedModel);
        } catch (Exception e) {
            throw new AssertionError("This should not throw an exception.");
        }

        expectedModel.undoCommand();

        assertCommandSuccess(UNDO_COMMAND, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        Clock.reset();
    }

}
