//@@author khoodehui

package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import seedu.momentum.MainApp;

/**
 * Represents a UI theme of the application.
 */
public class Theme {

    public static final String MESSAGE_CONSTRAINTS =
        "Theme should either be 'light' or 'dark'.";

    private static final String THEME_LIGHT = "/view/MomentumLight.css";
    private static final String THEME_DARK = "/view/MomentumDark.css";

    private ThemeType themeType;

    /**
     * Empty constructor required by Jackson.
     */
    public Theme() {
    }

    /**
     * Constructs a {@code Theme} with the specified theme type.
     *
<<<<<<< HEAD
     * @param themeType a valid theme type.
=======
     * @param themeType A valid theme type.
>>>>>>> 02b87a73eb8a43978e9849900eaa0f8a7cc0e890
     */
    public Theme(ThemeType themeType) {
        this.themeType = themeType;
    }

    /**
     * Constructs a {@code Theme} with the specified theme type expressed as a String.
     *
<<<<<<< HEAD
     * @param themeType a valid theme type.
=======
     * @param themeType A valid theme type.
>>>>>>> 02b87a73eb8a43978e9849900eaa0f8a7cc0e890
     */
    public Theme(String themeType) {
        requireNonNull(themeType);
        checkArgument(isValid(themeType), MESSAGE_CONSTRAINTS);
        this.themeType = ThemeType.valueOf(themeType.toUpperCase());
    }

    /**
     * Fetches the filepath of the CSS stylesheet corresponding to the current theme.
     *
     * @return The filepath of the stylesheet as a String.
     */
    public String getStylesheet() {
        assert(themeType == ThemeType.LIGHT || themeType == ThemeType.DARK);

        String stylesheetName =
            themeType == ThemeType.LIGHT
            ? THEME_LIGHT
            : THEME_DARK;

        return MainApp.class.getResource(stylesheetName).toString();
    }

    /**
     * Returns true if a given string is a valid theme.
<<<<<<< HEAD
=======
     *
     * @param theme The theme to check.
     * @return Whether the theme is valid.
>>>>>>> 02b87a73eb8a43978e9849900eaa0f8a7cc0e890
     */
    public static boolean isValid(String theme) {
        try {
            ThemeType.valueOf(theme.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.themeType.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Theme)) {
            return false;
        }

        Theme o = (Theme) other;

        return this.themeType == o.themeType;
    }

    @Override
    public int hashCode() {
        return this.themeType.hashCode();
    }


    /**
     * Represents the available themes in Momentum.
     */
    public enum ThemeType {
        LIGHT,
        DARK
    }
}
