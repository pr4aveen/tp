package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.commons.core.Theme;

public class UserPrefsTest {

    @Test
    public void setGuiWindowSettings_nullGuiWindowSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiWindowSettings(null));
    }

    @Test
    public void setGuiThemeSettings_nullGuiThemeSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiThemeSettings(null));
    }

    @Test
    public void setStatisticTimeframeSettings_nullStatisticTimeframeSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setStatisticTimeframeSettings(null));
    }

    @Test
    public void setProjectBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setProjectBookFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPref = new UserPrefs();

        // same object -> returns true
        assertTrue(userPref.equals(userPref));

        // same values -> returns true
        assertTrue(userPref.equals(new UserPrefs()));

        //different type -> returns false
        assertFalse(userPref.equals("3"));

        // different window settings -> returns false
        UserPrefs differentWindowSettings = new UserPrefs();
        differentWindowSettings.setGuiWindowSettings(new GuiWindowSettings(1, 1, -1, -1));
        assertFalse(userPref.equals(differentWindowSettings));

        // different theme settings -> returns false
        UserPrefs differentTheme = new UserPrefs()
            .returnChangedGuiThemeSettings(new GuiThemeSettings(new Theme(Theme.ThemeType.LIGHT)));
        assertFalse(userPref.equals(differentTheme));

        // different statistic timeframe settings -> returns false
        UserPrefs differentTimeframe = new UserPrefs().returnChangedStatisticsTimeframeSettings(
                new StatisticTimeframeSettings(new StatisticTimeframe(StatisticTimeframe.Timeframe.DAILY)));
        assertFalse(userPref.equals(differentTimeframe));

        // different project book filepath -> returns false
        UserPrefs differentFilePath = new UserPrefs().returnChangedProjectBookFilePath(Paths.get("mfeniofn"));
        assertFalse(userPref.equals(differentFilePath));
    }
}
