package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.model.Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.ProjectBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiWindowSettings(), modelManager.getGuiWindowSettings());
        assertEquals(new ProjectBook(), new ProjectBook(modelManager.getProjectBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setProjectBookFilePath(Paths.get("momentum/book/file/path"));
        userPrefs.setGuiWindowSettings(new GuiWindowSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setProjectBookFilePath(Paths.get("new/momentum/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiWindowSettings_nullGuiWindowSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiWindowSettings(null));
    }

    @Test
    public void setGuiWindowSettings_validGuiWindowSettings_setsGuiWindowSettings() {
        GuiWindowSettings guiWindowSettings = new GuiWindowSettings(1, 2, 3, 4);
        modelManager.setGuiWindowSettings(guiWindowSettings);
        assertEquals(guiWindowSettings, modelManager.getGuiWindowSettings());
    }

    @Test
    public void setProjectBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setProjectBookFilePath(null));
    }

    @Test
    public void setProjectBookFilePath_validPath_setsProjectBookFilePath() {
        Path path = Paths.get("momentum/book/file/path");
        modelManager.setProjectBookFilePath(path);
        assertEquals(path, modelManager.getProjectBookFilePath());
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTrackedItem(null));
    }

    @Test
    public void hasProject_projectNotInProjectBook_returnsFalse() {
        assertFalse(modelManager.hasTrackedItem(ALICE));
    }

    @Test
    public void hasProject_projectInProjectBook_returnsTrue() {
        modelManager.addTrackedItem(ALICE);
        assertTrue(modelManager.hasTrackedItem(ALICE));
    }

    @Test
    public void getFilteredProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTrackedItemList().remove(0));
    }
    @Test
    public void equals() {
        ProjectBook projectBook = new ProjectBookBuilder().withProject(ALICE).withProject(BENSON).build();
        ProjectBook differentProjectBook = new ProjectBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(projectBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(projectBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different projectBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentProjectBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredProjectList(
                new NameContainsKeywordsPredicate(FindType.ANY, Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(projectBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredProjectList(PREDICATE_SHOW_ALL_TRACKED_ITEMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setProjectBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(projectBook, differentUserPrefs)));
    }
}
