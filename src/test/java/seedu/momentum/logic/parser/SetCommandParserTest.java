package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_STATISTIC_TIMEFRAME_DESC;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_THEME_DESC;
import static seedu.momentum.logic.commands.CommandTestUtil.STATISTIC_TIMEFRAME_DESC_DAILY;
import static seedu.momentum.logic.commands.CommandTestUtil.STATISTIC_TIMEFRAME_DESC_MONTHLY;
import static seedu.momentum.logic.commands.CommandTestUtil.STATISTIC_TIMEFRAME_DESC_WEEKLY;
import static seedu.momentum.logic.commands.CommandTestUtil.THEME_DESC_DARK;
import static seedu.momentum.logic.commands.CommandTestUtil.THEME_DESC_LIGHT;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_STATISTIC_TIMEFRAME_DAILY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_STATISTIC_TIMEFRAME_MONTHLY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_STATISTIC_TIMEFRAME_WEEKLY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_THEME_DARK;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.logic.commands.SetCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.testutil.SettingsToChangeBuilder;

public class SetCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);

    private final SetCommandParser parser = new SetCommandParser();
    private final Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, " ", SetCommand.MESSAGE_NOT_CHANGED, model);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // theme
        assertParseFailure(parser, INVALID_THEME_DESC, Theme.MESSAGE_CONSTRAINTS, model);

        // statistic timeframe
        assertParseFailure(parser, INVALID_STATISTIC_TIMEFRAME_DESC, StatisticTimeframe.MESSAGE_CONSTRAINTS, model);
    }

    @Test
    public void parse_oneValidFieldSpecified_success() {
        // theme
        SetCommand.SettingsToChange settingsToChange =
            new SettingsToChangeBuilder().withTheme(VALID_THEME_LIGHT).build();
        SetCommand expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, THEME_DESC_LIGHT, expectedCommand, model);

        settingsToChange = new SettingsToChangeBuilder().withTheme(VALID_THEME_DARK).build();
        expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, THEME_DESC_DARK, expectedCommand, model);

        // statistic timeframe
        settingsToChange = new SettingsToChangeBuilder()
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_DAILY).build();
        expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, STATISTIC_TIMEFRAME_DESC_DAILY, expectedCommand, model);

        settingsToChange = new SettingsToChangeBuilder()
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_WEEKLY).build();
        expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, STATISTIC_TIMEFRAME_DESC_WEEKLY, expectedCommand, model);

        settingsToChange = new SettingsToChangeBuilder()
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_MONTHLY).build();
        expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, STATISTIC_TIMEFRAME_DESC_MONTHLY, expectedCommand, model);
    }

    @Test
    public void parse_allValidFields_success() {
        SetCommand.SettingsToChange settingsToChange = new SettingsToChangeBuilder().withTheme(VALID_THEME_DARK)
                .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_WEEKLY).build();
        SetCommand expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, THEME_DESC_DARK + STATISTIC_TIMEFRAME_DESC_WEEKLY, expectedCommand, model);

        settingsToChange = new SettingsToChangeBuilder().withTheme(VALID_THEME_LIGHT)
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_DAILY).build();
        expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, THEME_DESC_LIGHT + STATISTIC_TIMEFRAME_DESC_DAILY, expectedCommand, model);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "change theme please", MESSAGE_INVALID_FORMAT, model);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT, model);
    }

    @Test
    public void parse_multipleRepeatedFields_success() {
        String userInput = THEME_DESC_DARK + STATISTIC_TIMEFRAME_DESC_DAILY
            + THEME_DESC_LIGHT + STATISTIC_TIMEFRAME_DESC_MONTHLY;
        SetCommand.SettingsToChange settingsToChange = new SettingsToChangeBuilder()
            .withTheme(VALID_THEME_LIGHT).withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_MONTHLY).build();
        SetCommand expectedCommand = new SetCommand(settingsToChange);
        assertParseSuccess(parser, userInput, expectedCommand, model);
    }
}
