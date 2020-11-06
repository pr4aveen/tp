//@@author khoodehui

package seedu.momentum.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI theme settings.
 * Guarantees: immutable.
 */
public class GuiThemeSettings implements Serializable {

    private static final Theme DEFAULT_THEME = new Theme(Theme.ThemeType.DARK);

    private final Theme theme;

    /**
     * Constructs a {@code GuiThemeSettings} with the default theme.
     */
    public GuiThemeSettings() {
        theme = DEFAULT_THEME;
    }

    /**
     * Constructs a {@code GuiThemeSettings} with the specified theme.
     *
     * @param theme Theme to create the settings object with.
     */
    public GuiThemeSettings(Theme theme) {
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiThemeSettings)) { //this handles null as well.
            return false;
        }

        GuiThemeSettings o = (GuiThemeSettings) other;

        return theme.equals(o.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Theme : " + theme + "\n");
        return sb.toString();
    }
}
