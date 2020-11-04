package seedu.momentum.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

public class ReminderManagerTest {
    private static final Model modelUnmodified = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    private static final ReminderManager reminderManagerUnmodified = new ReminderManager(modelUnmodified);

    private static final BiFunction<BooleanProperty, BooleanProperty, Boolean> isBooleanPropertyEquals = (
            booleanProperty, booleanProperty2) -> booleanProperty.getValue() == booleanProperty2.getValue();
    private static final BiFunction<StringProperty, StringProperty, Boolean> isStringPropertyEquals = (stringProperty,
           stringProperty2) -> stringProperty.getValue().equals(stringProperty2.getValue());
    private ReminderManager reminderManager;

    @BeforeEach
    public void setUp() {
        reminderManager = new ReminderManager(new ModelManager(getTypicalProjectBook(), new UserPrefs()));
    }

    @Test
    public void updateCurrReminder() {
        reminderManager.updateCurrReminder(ALICE);
        StringProperty expectedReminder = new SimpleStringProperty();
        expectedReminder.set(String.format(ReminderManager.PROJECT_REMINDER, ALICE.getName()));
        assertTrue(isStringPropertyEquals.apply(expectedReminder, reminderManager.getReminder()));
    }

    @Test
    public void isReminderEmpty() {
        BooleanProperty expectedBoolean = new SimpleBooleanProperty();

        expectedBoolean.set(true);
        assertTrue(isBooleanPropertyEquals.apply(expectedBoolean, reminderManagerUnmodified.isReminderEmpty()));

        expectedBoolean.set(false);
        reminderManager.updateCurrReminder(ALICE);
        assertTrue(isBooleanPropertyEquals.apply(expectedBoolean, reminderManager.isReminderEmpty()));
    }

    @Test
    public void removeReminder() {
        reminderManager.updateCurrReminder(ALICE);
        reminderManager.removeReminder();
        assertTrue(reminderManager.isReminderEmpty().get());
    }

    @Test
    public void getReminder() {
        StringProperty expectedStringProperty = new SimpleStringProperty();
        expectedStringProperty.set(String.format(ReminderManager.PROJECT_REMINDER, ALICE.getName()));
        reminderManager.updateCurrReminder(ALICE);
        assertTrue(isStringPropertyEquals.apply(expectedStringProperty, reminderManager.getReminder()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(reminderManager.equals(reminderManagerUnmodified));

        // same object -> return true
        assertTrue(reminderManager.equals(reminderManager));

        // null -> return false
        assertFalse(reminderManager.equals(null));

        // different types -> return false
        assertFalse(reminderManager.equals(5));

        // different currReminder -> returns false
        reminderManager.updateCurrReminder(ALICE);
        assertFalse(reminderManager.equals(reminderManagerUnmodified));
    }
}
