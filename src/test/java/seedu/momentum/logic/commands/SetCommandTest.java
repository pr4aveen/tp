package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.SETTINGS_ONE;
import static seedu.momentum.logic.commands.CommandTestUtil.SETTINGS_TWO;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_STATISTIC_TIMEFRAME_MONTHLY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.testutil.SettingsToChangeBuilder;

public class SetCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void equals() {
        SetCommand standardCommand = new SetCommand(SETTINGS_ONE);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> returns true
        SetCommand commandWithSameValues = new SetCommand(SETTINGS_ONE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ExitCommand()));

        // different settings to change -> returns false
        assertFalse(standardCommand.equals(new SetCommand(SETTINGS_TWO)));
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        SetCommand.SettingsToChange settingsToChange = new SettingsToChangeBuilder().withTheme(VALID_THEME_LIGHT)
                .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_MONTHLY).build();
        SetCommand setCommand = new SetCommand(settingsToChange);
        String expectedMessage = SetCommand.MESSAGE_UPDATE_SETTINGS_SUCCESS;

        expectedModel.setGuiThemeSettings(new GuiThemeSettings(new Theme(VALID_THEME_LIGHT)));
        expectedModel.setStatisticTimeframeSettings(
            new StatisticTimeframeSettings(new StatisticTimeframe(VALID_STATISTIC_TIMEFRAME_MONTHLY)));
        expectedModel.commitToHistory();

        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneFieldSpecified_success() {
        SetCommand.SettingsToChange settingsToChange = new SettingsToChangeBuilder()
            .withTheme(VALID_THEME_LIGHT).build();
        SetCommand setCommand = new SetCommand(settingsToChange);
        String expectedMessage = SetCommand.MESSAGE_UPDATE_SETTINGS_SUCCESS;

        expectedModel.setGuiThemeSettings(new GuiThemeSettings(new Theme(VALID_THEME_LIGHT)));
        expectedModel.commitToHistory();

        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        SetCommand setCommand = new SetCommand(new SetCommand.SettingsToChange());
        String expectedMessage = SetCommand.MESSAGE_UPDATE_SETTINGS_SUCCESS;
        expectedModel.commitToHistory();

        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }
}
