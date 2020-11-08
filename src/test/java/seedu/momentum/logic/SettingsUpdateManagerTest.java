package seedu.momentum.logic;

import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SettingsUpdateManagerTest {

    @Test
    public void initSettingsUpdateManager_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SettingsUpdateManager.initSettingsUpdateManager(null, null));
    }
}
