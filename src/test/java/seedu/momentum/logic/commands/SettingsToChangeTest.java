package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.SETTINGS_ONE;
import static seedu.momentum.logic.commands.CommandTestUtil.SETTINGS_TWO;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_STATISTIC_TIMEFRAME_DAILY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.SettingsToChangeBuilder;


public class SettingsToChangeTest {

    @Test
    public void equals() {
        // same values -> returns true
        SetCommand.SettingsToChange settingsToChangeWithSameValues = new SetCommand.SettingsToChange(SETTINGS_ONE);
        assertTrue(SETTINGS_ONE.equals(settingsToChangeWithSameValues));

        // same object -> returns true
        assertTrue(SETTINGS_ONE.equals(SETTINGS_ONE));

        // null -> returns false
        assertFalse(SETTINGS_ONE.equals(null));

        // different types -> returns false
        assertFalse(SETTINGS_ONE.equals(5));

        // different values -> returns false
        assertFalse(SETTINGS_ONE.equals(SETTINGS_TWO));

        // different theme -> returns false
        SetCommand.SettingsToChange editedSettingsOne = new SettingsToChangeBuilder(SETTINGS_ONE)
            .withTheme(VALID_THEME_LIGHT).build();
        assertFalse(SETTINGS_ONE.equals(editedSettingsOne));

        // different statistic timeframe -> returns false
        editedSettingsOne = new SettingsToChangeBuilder(SETTINGS_ONE)
            .withStatisticTimeframe(VALID_STATISTIC_TIMEFRAME_DAILY).build();
        assertFalse(SETTINGS_ONE.equals(editedSettingsOne));
    }
}
