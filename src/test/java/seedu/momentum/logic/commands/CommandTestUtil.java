package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.momentum.logic.parser.CliSyntax.SET_STATISTIC_TIMEFRAME;
import static seedu.momentum.logic.parser.CliSyntax.SET_THEME;
import static seedu.momentum.logic.parser.CliSyntax.SORT_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_TYPE;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.EditTrackedItemDescriptorBuilder;
import seedu.momentum.testutil.SettingsToChangeBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DESCRIPTION_AMY = "Loves coffee";
    public static final String VALID_DESCRIPTION_BOB = "Hates coffee";
    public static final CompletionStatus VALID_COMPLETION_STATUS_AMY = new CompletionStatus();
    public static final CompletionStatus VALID_COMPLETION_STATUS_BOB = new CompletionStatus().reverse();
    public static final String VALID_CREATED_DATE_AMY = "2019-12-02";
    public static final String VALID_CREATED_DATE_BOB = "2019-10-02";
    public static final String VALID_DEADLINE_DATE_AMY = "2030-12-02";
    public static final String VALID_DEADLINE_DATE_BOB = "2030-10-02";
    public static final String VALID_DEADLINE_TIME_AMY = "11:42:53";
    public static final String VALID_REMINDER_AMY = "2030-06-22T05:42:53";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String COMPLETION_STATUS_DESC_BOB = " " + PREFIX_COMPLETION_STATUS;
    public static final String DEADLINE_DATE_DESC_AMY = " " + PREFIX_DEADLINE_DATE + VALID_DEADLINE_DATE_AMY;
    public static final String DEADLINE_DATE_DESC_BOB = " " + PREFIX_DEADLINE_DATE + VALID_DEADLINE_DATE_BOB;
    public static final String DEADLINE_TIME_DESC_AMY = " " + PREFIX_DEADLINE_TIME + VALID_DEADLINE_TIME_AMY;
    public static final String REMINDER_DESC_AMY = " " + PREFIX_REMINDER + VALID_REMINDER_AMY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DEADLINE_DATE_DESC = " " + PREFIX_DEADLINE_DATE + "2020-91-64";
    public static final String INVALID_DEADLINE_TIME_DESC = " " + PREFIX_DEADLINE_TIME + "09:91:12";
    public static final String INVALID_REMINDER_DESC = " " + PREFIX_REMINDER + VALID_DEADLINE_DATE_AMY
            + VALID_DEADLINE_TIME_AMY; // 'T' is required to separate date and time
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_SORT_TYPE = " " + SORT_TYPE + "nomnom";
    public static final String INVALID_SORT_ORDER = " " + SORT_ORDER + "can you not";
    public static final String VALID_ASCENDING_SORT_ORDER = " " + SORT_ORDER + "asc";
    public static final String VALID_DESCENDING_SORT_ORDER = " " + SORT_ORDER + "dsc";
    public static final String VALID_ALPHA_SORT_TYPE = " " + SORT_TYPE + SortCommand.INPUT_ALPHA_TYPE
            + " " + PREFIX_COMPLETION_STATUS;
    public static final String VALID_DEADLINE_SORT_TYPE = " " + SORT_TYPE + SortCommand.INPUT_DEADLINE_TYPE
            + " " + PREFIX_COMPLETION_STATUS;
    public static final String VALID_CREATED_DATE_SORT_TYPE = " " + SORT_TYPE + SortCommand.INPUT_CREATED_TYPE;

    public static final String INVALID_THEME = "transparent";
    public static final String INVALID_STATISTIC_TIMEFRAME = "yearly";
    public static final String VALID_THEME_DARK = "dark";
    public static final String VALID_THEME_LIGHT = "light";
    public static final String VALID_STATISTIC_TIMEFRAME_DAILY = "daily";
    public static final String VALID_STATISTIC_TIMEFRAME_WEEKLY = "weekly";
    public static final String VALID_STATISTIC_TIMEFRAME_MONTHLY = "monthly";

    public static final String THEME_DESC_DARK = " " + SET_THEME + VALID_THEME_DARK;
    public static final String THEME_DESC_LIGHT = " " + SET_THEME + VALID_THEME_LIGHT;
    public static final String STATISTIC_TIMEFRAME_DESC_DAILY = " " + SET_STATISTIC_TIMEFRAME
        + VALID_STATISTIC_TIMEFRAME_DAILY;
    public static final String STATISTIC_TIMEFRAME_DESC_WEEKLY = " " + SET_STATISTIC_TIMEFRAME
        + VALID_STATISTIC_TIMEFRAME_WEEKLY;
    public static final String STATISTIC_TIMEFRAME_DESC_MONTHLY = " " + SET_STATISTIC_TIMEFRAME
        + VALID_STATISTIC_TIMEFRAME_MONTHLY;

    public static final String INVALID_THEME_DESC = " " + SET_THEME + INVALID_THEME;
    public static final String INVALID_STATISTIC_TIMEFRAME_DESC = " " + SET_STATISTIC_TIMEFRAME
        + INVALID_STATISTIC_TIMEFRAME;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTrackedItemDescriptor DESC_AMY;
    public static final EditCommand.EditTrackedItemDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDescription(VALID_DESCRIPTION_AMY)
                .withDeadline(VALID_DEADLINE_DATE_AMY, VALID_DEADLINE_TIME_AMY, VALID_CREATED_DATE_AMY)
                .withReminder(VALID_REMINDER_AMY)
                .withTags(VALID_TAG_FRIEND)
                .build();
        DESC_BOB = new EditTrackedItemDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDescription(VALID_DESCRIPTION_BOB)
                .withCompletionStatus(VALID_COMPLETION_STATUS_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .build();
    }

    public static final SetCommand.SettingsToChange SETTINGS_ONE;
    public static final SetCommand.SettingsToChange SETTINGS_TWO;

    static {
        SETTINGS_ONE = new SettingsToChangeBuilder().withTheme(VALID_THEME_DARK)
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_MONTHLY)
            .build();

        SETTINGS_TWO = new SettingsToChangeBuilder().withTheme(VALID_THEME_LIGHT)
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_DAILY)
            .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertTrue(expectedModel.equals(actualModel));
        } catch (ParseException | CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    //    /**
    //     * Executes the given {@code command}, confirms that <br>
    //     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
    //     */
    //    public static void assertUndoCommandSuccess(Command command, Model actualModel,
    //                                                String expectedMessage) {
    //        try {
    //            CommandResult result = command.execute(actualModel);
    //            CommandResult expectedCommandResult = new CommandResult(expectedMessage);
    //            assertEquals(result, expectedCommandResult);
    //        } catch (ParseException | CommandException ce) {
    //            throw new AssertionError("Execution of command should not fail.", ce);
    //        }
    //    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the project book, filtered project list and selected project in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ProjectBook expectedProjectBook = new ProjectBook(actualModel.getProjectBook());
        List<TrackedItem> expectedFilteredList = new ArrayList<>(actualModel.getDisplayList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedProjectBook, actualModel.getProjectBook());
        assertEquals(expectedFilteredList, actualModel.getDisplayList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the project at the given {@code targetIndex} in the
     * {@code model}'s project book.
     */
    public static void showProjectAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getDisplayList().size());
        assertSame(model.getViewMode(), ViewMode.PROJECTS);

        TrackedItem trackedItem = model.getDisplayList().get(targetIndex.getZeroBased());
        final String[] splitName = trackedItem.getName().fullName.split("\\s+");
        model.updatePredicate(
            new NameContainsKeywordsPredicate(FindType.ALL, Arrays.asList(splitName)));

        assertEquals(1, model.getDisplayList().size());
    }

    /**
     * Returns the project at the given {@code targetIndex} in the {@code model}'s project book.
     */
    public static Project getProjectAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getDisplayList().size());

        TrackedItem trackedItem = model.getDisplayList().get(targetIndex.getZeroBased());
        return (Project) trackedItem;
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s project book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getDisplayList().size());
        assertSame(model.getViewMode(), ViewMode.TASKS);

        TrackedItem trackedItem = model.getDisplayList().get(targetIndex.getZeroBased());
        final String[] splitName = trackedItem.getName().fullName.split("\\s+");
        model.updatePredicate(
                new NameContainsKeywordsPredicate(FindType.ALL, Arrays.asList(splitName)));
        assertEquals(1, model.getDisplayList().size());
    }

}
