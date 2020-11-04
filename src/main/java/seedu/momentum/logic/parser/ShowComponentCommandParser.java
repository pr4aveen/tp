package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.momentum.logic.commands.ShowComponentCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

public class ShowComponentCommandParser implements Parser<ShowComponentCommand> {
    public enum ComponentType {
        TAGS;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ShowComponentCommand
     * and returns an ShowComponentCommand object for execution.
     *
     * @param model the current model.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ShowComponentCommand parse(String args, Model model) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        List<ComponentType> componentTypes = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            componentTypes.add(ComponentType.TAGS);
        }

        if (componentTypes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowComponentCommand.MESSAGE_USAGE));
        }

        return new ShowComponentCommand(componentTypes);
    }

}
