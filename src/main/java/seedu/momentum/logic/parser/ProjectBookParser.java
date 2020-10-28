package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.momentum.logic.commands.AddCommand;
import seedu.momentum.logic.commands.ClearCommand;
import seedu.momentum.logic.commands.Command;
import seedu.momentum.logic.commands.DeleteCommand;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.logic.commands.ExitCommand;
import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.logic.commands.HelpCommand;
import seedu.momentum.logic.commands.HomeCommand;
import seedu.momentum.logic.commands.ListCommand;
import seedu.momentum.logic.commands.ProjectViewCommand;
import seedu.momentum.logic.commands.RedoCommand;
import seedu.momentum.logic.commands.SetCommand;
import seedu.momentum.logic.commands.ShowComponentCommand;
import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.logic.commands.StartCommand;
import seedu.momentum.logic.commands.StopCommand;
import seedu.momentum.logic.commands.UndoCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

/**
 * Parses user input.
 */
public class ProjectBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string.
     * @param model     the current model manager.
     * @return the command based on the user input.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput, Model model) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, model);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, model);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, model);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments, model);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments, model);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments, model);

        case StopCommand.COMMAND_WORD:
            return new StopCommandParser().parse(arguments, model);

        case ProjectViewCommand.COMMAND_WORD:
            return new ProjectViewCommandParser().parse(arguments, model);

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        case SetCommand.COMMAND_WORD:
            return new SetCommandParser().parse(arguments, model);

        case ShowComponentCommand.COMMAND_WORD:
            return new ShowComponentCommandParser().parse(arguments, model);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
