package seedu.momentum.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.momentum.commons.core.GuiWindowSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiWindowSettings guiWindowSettings = new GuiWindowSettings();
    private Path projectBookFilePath = Paths.get("data" , "projectbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiWindowSettings(newUserPrefs.getGuiWindowSettings());
        setProjectBookFilePath(newUserPrefs.getProjectBookFilePath());
    }

    public GuiWindowSettings getGuiWindowSettings() {
        return guiWindowSettings;
    }

    public void setGuiWindowSettings(GuiWindowSettings guiWindowSettings) {
        requireNonNull(guiWindowSettings);
        this.guiWindowSettings = guiWindowSettings;
    }

    public Path getProjectBookFilePath() {
        return projectBookFilePath;
    }

    public void setProjectBookFilePath(Path projectBookFilePath) {
        requireNonNull(projectBookFilePath);
        this.projectBookFilePath = projectBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiWindowSettings.equals(o.guiWindowSettings)
                && projectBookFilePath.equals(o.projectBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiWindowSettings, projectBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Window Settings : " + guiWindowSettings);
        sb.append("\nLocal data file location : " + projectBookFilePath);
        return sb.toString();
    }

}
