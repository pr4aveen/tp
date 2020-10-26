package seedu.momentum.logic.parser;

import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;

import seedu.momentum.logic.commands.ShowComponentCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

public class ShowComponentCommandParser implements Parser<ShowComponentCommand> {
    public static final String NO_COMPONENT_SPECIFIED = "No component specified.";

    public enum ComponentType {
        REMINDER;

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
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowComponentCommand parse(String args, Model model) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMINDER);
        if (argMultimap.getValue(PREFIX_REMINDER).isPresent()) {
            return new ShowComponentCommand(ComponentType.REMINDER);
        }
        throw new ParseException(NO_COMPONENT_SPECIFIED);
    }

}
