package seedu.momentum.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.momentum.logic.commands.SortCommand.INPUT_ALPHA_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_TYPE;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.AddProjectCommand;
import seedu.momentum.logic.commands.ClearCommand;
import seedu.momentum.logic.commands.DeleteCommand;
import seedu.momentum.logic.commands.DeleteProjectCommand;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.logic.commands.EditProjectCommand;
import seedu.momentum.logic.commands.ExitCommand;
import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.logic.commands.HelpCommand;
import seedu.momentum.logic.commands.ListCommand;
import seedu.momentum.logic.commands.ShowComponentCommand;
import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.logic.commands.StartCommand;
import seedu.momentum.logic.commands.StartProjectCommand;
import seedu.momentum.logic.commands.StopCommand;
import seedu.momentum.logic.commands.StopProjectCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.EditTrackedItemDescriptorBuilder;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.ProjectUtil;

public class ProjectBookParserTest {

    private final ProjectBookParser parser = new ProjectBookParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void parseCommand_add() throws Exception {
        Project project = new ProjectBuilder().withCurrentCreatedDate().build();
        AddProjectCommand command = (AddProjectCommand) parser.parseCommand(ProjectUtil.getAddCommand(project), model);
        assertEquals(new AddProjectCommand(project), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, model) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", model) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), model);
        assertEquals(new DeleteProjectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Project project = new ProjectBuilder().build();
        EditCommand.EditTrackedItemDescriptor descriptor = new EditTrackedItemDescriptorBuilder(project).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST.getOneBased() + " "
            + ProjectUtil.getEditProjectDescriptorDetails(descriptor), model);
        assertEquals(new EditProjectCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, model) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", model) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + PREFIX_NAME + String.join(" ", keywords), model);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(FindType.ANY, keywords)), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD, model) instanceof SortCommand);
        SortCommand command = (SortCommand) parser.parseCommand(SortCommand.COMMAND_WORD + " "
                + SORT_ORDER + SortCommand.INPUT_ASCENDING_ORDER + " " + SORT_TYPE + INPUT_ALPHA_TYPE + " "
                + PREFIX_COMPLETION_STATUS, model);
        assertEquals(new SortCommand(SortType.ALPHA, true, false, true), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, model) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", model) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, model) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", model) instanceof ListCommand);
    }

    @Test
    public void parseCommand_start() throws Exception {
        StartCommand command = (StartCommand) parser.parseCommand(
                StartCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), model);
        assertEquals(new StartProjectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_stop() throws Exception {
        StopCommand command = (StopCommand) parser.parseCommand(
                StopCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), model);
        assertEquals(new StopProjectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_showComponent() throws Exception {
        ShowComponentCommand command = (ShowComponentCommand) parser.parseCommand(
                ShowComponentCommand.COMMAND_WORD + " " + PREFIX_REMINDER, model);
        assertEquals(new ShowComponentCommand(ShowComponentCommandParser.ComponentType.REMINDER), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", model));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", model));
    }
}
