package seedu.momentum.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CliSyntax.SET_STATISTIC_TIMEFRAME;
import static seedu.momentum.logic.parser.CliSyntax.SET_THEME;

import seedu.momentum.logic.commands.SetCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

public class SetCommandParser implements Parser<SetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     *
     * @param model the current model.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public SetCommand parse(String args, Model model) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, SET_THEME, SET_STATISTIC_TIMEFRAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        SetCommand.SettingsToChange settingsToChange = new SetCommand.SettingsToChange();

        if (argMultimap.getValue(SET_THEME).isPresent()) {
            settingsToChange.setTheme(ParserUtil.parseTheme(argMultimap.getValue(SET_THEME).get()));
        }

        if (argMultimap.getValue(SET_STATISTIC_TIMEFRAME).isPresent()) {
            settingsToChange.setStatTimeframe(ParserUtil.parseStatisticTimeframe(
                argMultimap.getValue(SET_STATISTIC_TIMEFRAME).get()));
        }

        if (!settingsToChange.isAnySettingChanged()) {
            throw new ParseException(SetCommand.MESSAGE_NOT_CHANGED);
        }

        return new SetCommand(settingsToChange);
    }
}
