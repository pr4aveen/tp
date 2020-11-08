package seedu.momentum.model;

import java.nio.file.Path;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    /**
     * Returns the gui window settings of the application.
     */
    GuiWindowSettings getGuiWindowSettings();

    //@@author khoodehui
    /**
     * Returns the gui theme settings of the application.
     */
    GuiThemeSettings getGuiThemeSettings();

    /**
     * Returns the statistic timeframe settings of the application.
     */
    StatisticTimeframeSettings getStatisticTimeframeSettings();
    //@@author

    /**
     * Returns the file path of the project book.
     */
    Path getProjectBookFilePath();

}
