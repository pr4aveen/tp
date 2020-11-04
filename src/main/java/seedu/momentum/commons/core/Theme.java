package seedu.momentum.commons.core;

import seedu.momentum.MainApp;

/**
 * Represents a UI theme of the application.
 */
public class Theme {

    public static final String MESSAGE_CONSTRAINTS =
        "Theme should either be 'light' or 'dark'.";

    private ThemeType themeType;

    /**
     * Empty constructor required by Jackson.
     */
    public Theme() {
    }

    public Theme(ThemeType themeType) {
        this.themeType = themeType;
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
            ? "MomentumLight.css"
            : "MomentumDark.css";

        return MainApp.class.getResource("/view/" + stylesheetName).toString();
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


    public enum ThemeType {
        LIGHT, DARK
    }
}
