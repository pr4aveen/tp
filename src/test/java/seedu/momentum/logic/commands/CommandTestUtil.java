package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.momentum.logic.parser.CliSyntax.SORT_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_TYPE;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.EditProjectDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DESCRIPTION_AMY = "Loves coffee";
    public static final String VALID_DESCRIPTION_BOB = "Hates coffee";
    public static final String VALID_CREATED_DATE_AMY = "2019-12-02";
    public static final String VALID_CREATED_DATE_BOB = "2019-10-02";
    public static final String VALID_DEADLINE_DATE_AMY = "2030-12-02";
    public static final String VALID_DEADLINE_DATE_BOB = "2030-10-02";
    public static final String VALID_DEADLINE_TIME_AMY = "11:42:53";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String DEADLINE_DATE_DESC_AMY = " " + PREFIX_DEADLINE_DATE + VALID_DEADLINE_DATE_AMY;
    public static final String DEADLINE_DATE_DESC_BOB = " " + PREFIX_DEADLINE_DATE + VALID_DEADLINE_DATE_BOB;
    public static final String DEADLINE_TIME_DESC_AMY = " " + PREFIX_DEADLINE_TIME + VALID_DEADLINE_TIME_AMY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DEADLINE_DATE_DESC = " " + PREFIX_DEADLINE_DATE + "2020-91-64";
    public static final String INVALID_DEADLINE_TIME_DESC = " " + PREFIX_DEADLINE_TIME + "09:91:12";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_SORT_TYPE = " " + SORT_TYPE + "nomnom";
    public static final String INVALID_SORT_ORDER = " " + SORT_ORDER + "can you not";
    public static final String VALID_ASCENDING_SORT_ORDER = " " + SORT_ORDER + "asc";
    public static final String VALID_DESCENDING_SORT_ORDER = " " + SORT_ORDER + "dsc";
    public static final String VALID_ALPHA_SORT_TYPE = " " + SORT_TYPE + "alpha";
    public static final String VALID_DEADLINE_SORT_TYPE = " " + SORT_TYPE + "deadline";
    public static final String VALID_CREATED_DATE_SORT_TYPE = " " + SORT_TYPE + "created";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditProjectDescriptor DESC_AMY;
    public static final EditCommand.EditProjectDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDescription(VALID_DESCRIPTION_AMY)
                .withDeadline(VALID_DEADLINE_DATE_AMY, VALID_DEADLINE_TIME_AMY, VALID_CREATED_DATE_AMY)
                .withTags(VALID_TAG_FRIEND)
                .build();
        DESC_BOB = new EditProjectDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDescription(VALID_DESCRIPTION_BOB)
                .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
            assertEquals(expectedModel, actualModel);
        } catch (ParseException | CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

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
        List<Project> expectedFilteredList = new ArrayList<>(actualModel.getFilteredProjectList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedProjectBook, actualModel.getProjectBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredProjectList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the project at the given {@code targetIndex} in the
     * {@code model}'s project book.
     */
    public static void showProjectAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredProjectList().size());

        Project project = model.getFilteredProjectList().get(targetIndex.getZeroBased());
        final String[] splitName = project.getName().fullName.split("\\s+");
        model.updateFilteredProjectList(
            new NameContainsKeywordsPredicate(FindType.ANY, Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredProjectList().size());
    }

}
