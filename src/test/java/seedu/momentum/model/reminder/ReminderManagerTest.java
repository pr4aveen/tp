package seedu.momentum.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.momentum.model.ProjectBook;

public class ReminderManagerTest {
    private final ProjectBook projectBookUnmodified = getTypicalProjectBook();
    private final ReminderManager reminderManagerUnmodified = new ReminderManager(projectBookUnmodified);
    private ProjectBook projectBook;
    private ReminderManager reminderManager;

    private BiFunction<BooleanProperty, BooleanProperty, Boolean> isBooleanPropertyEquals = (booleanProperty,
         booleanProperty2) -> booleanProperty.getValue() == booleanProperty2.getValue();
    private BiFunction<StringProperty, StringProperty, Boolean> isStringPropertyEquals = (stringProperty,
         stringProperty2) -> stringProperty.getValue().equals(stringProperty2.getValue());

    private void resetProjectBookAndReminderManager() {
        projectBook = getTypicalProjectBook();
        reminderManager = new ReminderManager(projectBook);
    }

    @Test
    public void updateCurrReminder() {
        resetProjectBookAndReminderManager();
        reminderManager.updateCurrReminder(ALICE);
        StringProperty expectedReminder = new SimpleStringProperty();
        expectedReminder.set(String.format(ReminderManager.PROJECT_REMINDER, ALICE.getName()));
        assertTrue(isStringPropertyEquals.apply(expectedReminder, reminderManager.getReminder()));
    }

    @Test
    public void isReminderEmpty() {
        resetProjectBookAndReminderManager();
        BooleanProperty expectedBoolean = new SimpleBooleanProperty();
        expectedBoolean.set(true);
        assertTrue(isBooleanPropertyEquals.apply(expectedBoolean, reminderManagerUnmodified.isReminderEmpty()));

        expectedBoolean.set(false);
        reminderManager.updateCurrReminder(ALICE);
        assertTrue(isBooleanPropertyEquals.apply(expectedBoolean, reminderManager.isReminderEmpty()));
    }

    @Test
    public void removeReminder() {
        resetProjectBookAndReminderManager();
        reminderManager.updateCurrReminder(ALICE);
        reminderManager.removeReminder();
        assertTrue(reminderManager.isReminderEmpty().get());
    }

    @Test
    public void getReminder() {
        resetProjectBookAndReminderManager();
        StringProperty expectedStringProperty = new SimpleStringProperty();
        expectedStringProperty.set(String.format(ReminderManager.PROJECT_REMINDER, ALICE.getName()));
        reminderManager.updateCurrReminder(ALICE);
        assertTrue(isStringPropertyEquals.apply(expectedStringProperty, reminderManager.getReminder()));
    }

    @Test
    public void equals() {
        resetProjectBookAndReminderManager();

        // same values -> returns true
        assertTrue(reminderManager.equals(reminderManagerUnmodified));

        // same object -> return true
        assertTrue(reminderManager.equals(reminderManager));

        // null -> return false
        assertFalse(reminderManager.equals(null));

        // different types -> return false
        assertFalse(reminderManager.equals(5));

        // different project book -> return false
        assertFalse(reminderManager.equals(new ReminderManager(new ProjectBook())));

        // different currReminder
        reminderManager.updateCurrReminder(ALICE);
        assertFalse(reminderManager.equals(reminderManagerUnmodified));
    }
}
