package seedu.momentum.commons.core;

import java.nio.file.Paths;

/**
 * UI theme of the application.
 */
public class Theme {

    public static final String MESSAGE_CONSTRAINTS =
        "Theme should either be light or dark.";

    private ThemeType themeType;

    public Theme(ThemeType themeType) {
        this.themeType = themeType;
    }

    /**
     * Fetches the filepath of the CSS stylesheet corresponding to the current theme.
     */
    public String getStylesheet() {
        assert(themeType == ThemeType.LIGHT || themeType == ThemeType.DARK);

        String stylesheetName =
            themeType == ThemeType.LIGHT
            ? "MomentumLight.css"
            : "MomentumDark.css";

        return Paths.get("view", stylesheetName).toString();
    }

    public enum ThemeType {
        LIGHT, DARK
    }
}
