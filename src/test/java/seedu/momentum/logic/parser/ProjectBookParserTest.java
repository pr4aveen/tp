package seedu.momentum.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.momentum.logic.commands.SortCommand.INPUT_ALPHA_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.SORT_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_TYPE;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.AddCommand;
import seedu.momentum.logic.commands.ClearCommand;
import seedu.momentum.logic.commands.DeleteCommand;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.logic.commands.ExitCommand;
import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.logic.commands.HelpCommand;
import seedu.momentum.logic.commands.ListCommand;
import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.logic.commands.StartCommand;
import seedu.momentum.logic.commands.StopCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.EditProjectDescriptorBuilder;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.ProjectUtil;

public class ProjectBookParserTest {

    private final ProjectBookParser parser = new ProjectBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Project project = new ProjectBuilder().withCurrentCreatedDate().build();
        AddCommand command = (AddCommand) parser.parseCommand(ProjectUtil.getAddCommand(project));
        assertEquals(new AddCommand(project), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PROJECT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Project project = new ProjectBuilder().build();
        EditCommand.EditTrackedItemDescriptor descriptor = new EditProjectDescriptorBuilder(project).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PROJECT.getOneBased() + " " + ProjectUtil.getEditProjectDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PROJECT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + PREFIX_NAME + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(FindType.ANY, keywords)), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        SortCommand command = (SortCommand) parser.parseCommand(SortCommand.COMMAND_WORD + " "
                + SORT_ORDER + SortCommand.INPUT_ASCENDING_ORDER + " " + SORT_TYPE + INPUT_ALPHA_TYPE);
        assertEquals(new SortCommand(SortType.ALPHA, true, false), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_start() throws Exception {
        StartCommand command = (StartCommand) parser.parseCommand(
                StartCommand.COMMAND_WORD + " " + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new StartCommand(INDEX_FIRST_PROJECT), command);
    }

    @Test
    public void parseCommand_stop() throws Exception {
        StopCommand command = (StopCommand) parser.parseCommand(
                StopCommand.COMMAND_WORD + " " + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new StopCommand(INDEX_FIRST_PROJECT), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
