package seedu.momentum.model;

import java.nio.file.Path;

import seedu.momentum.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getProjectBookFilePath();

}
