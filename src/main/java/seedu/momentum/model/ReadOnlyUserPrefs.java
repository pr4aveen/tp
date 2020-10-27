package seedu.momentum.model;

import java.nio.file.Path;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiWindowSettings getGuiWindowSettings();

    GuiThemeSettings getGuiThemeSettings();

    Path getProjectBookFilePath();

}
