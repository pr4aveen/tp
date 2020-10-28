package seedu.momentum.model;

import java.nio.file.Path;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiWindowSettings getGuiWindowSettings();

    GuiThemeSettings getGuiThemeSettings();

    StatisticTimeframeSettings getStatisticTimeframeSettings();

    Path getProjectBookFilePath();

}
