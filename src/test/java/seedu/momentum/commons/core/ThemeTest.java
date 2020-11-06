package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ThemeTest {

    private static final Theme LIGHT_THEME = new Theme(Theme.ThemeType.LIGHT);
    private static final Theme DARK_THEME = new Theme(Theme.ThemeType.DARK);

    @Test
    public void constructor_invalidThemeType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Theme("fnuiw"));
    }

    @Test
    public void isValid() {
        // null -> throws exception
        assertThrows(NullPointerException.class, () -> Theme.isValid(null));

        // invalid theme type -> return false
        assertFalse(Theme.isValid("transparent"));

        // valid theme type -> return true
        assertTrue(Theme.isValid("light"));
        assertTrue(Theme.isValid("dark"));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(LIGHT_THEME.equals(LIGHT_THEME));

        // same theme type -> returns true
        assertTrue(LIGHT_THEME.equals(new Theme(Theme.ThemeType.LIGHT)));

        // different types -> returns false
        assertFalse(LIGHT_THEME.equals("100"));

        // null -> return false
        assertFalse(LIGHT_THEME.equals(null));

        // different theme type -> return false
        assertFalse(LIGHT_THEME.equals(DARK_THEME));
    }

}
