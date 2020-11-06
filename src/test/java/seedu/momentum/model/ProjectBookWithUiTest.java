package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.testutil.ProjectBuilder;

public class ProjectBookWithUiTest {

    private static final Project VALID_PROJECT = new ProjectBuilder().withName("TEST").build();
    private static final Comparator<TrackedItem> NAME_COMPARE = new NameCompare();
    private static final UserPrefs USER_PREFS = new UserPrefs();

    private static final ProjectBookWithUi PROJECT_BOOK_WITH_UI =
            new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS, VALID_PROJECT,
                    Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, USER_PREFS);

    @Test
    public void constructor() {
        assertEquals(ViewMode.TASKS, PROJECT_BOOK_WITH_UI.getViewMode());
        assertEquals(VALID_PROJECT, PROJECT_BOOK_WITH_UI.getProject());
        assertEquals(Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, PROJECT_BOOK_WITH_UI.getPredicate());
        assertEquals(NAME_COMPARE, PROJECT_BOOK_WITH_UI.getComparator());
        assertEquals(true, PROJECT_BOOK_WITH_UI.isTagsVisible());
        assertEquals(USER_PREFS, PROJECT_BOOK_WITH_UI.getUserPrefs());
    }

    @Test
    public void getViewMode_returnsViewMode() {
        ViewMode viewMode = PROJECT_BOOK_WITH_UI.getViewMode();
        assertEquals(viewMode, ViewMode.TASKS);
    }

    @Test
    public void getProject_returnsProject() {
        Project project = PROJECT_BOOK_WITH_UI.getProject();
        assertEquals(project, VALID_PROJECT);
    }

    @Test
    public void getPredicate_returnsPredicate() {
        Predicate<TrackedItem> predicate = PROJECT_BOOK_WITH_UI.getPredicate();
        assertEquals(predicate, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Test
    public void getComparator_returnsComparator() {
        Comparator<TrackedItem> comparator = PROJECT_BOOK_WITH_UI.getComparator();
        assertEquals(comparator, NAME_COMPARE);
    }

    @Test
    public void isTagsVisible_returnsTrue() {
        assertEquals(true, PROJECT_BOOK_WITH_UI.isTagsVisible());
    }

    @Test
    public void isTagsVisible_returnsFalse() {
        ProjectBookWithUi projectBookTagsInvisible =
                new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS, VALID_PROJECT,
                        Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, false, USER_PREFS);
        assertEquals(false, projectBookTagsInvisible.isTagsVisible());
    }

    @Test
    public void getUserPrefs_returnsUserPref() {
        ReadOnlyUserPrefs userPrefs = PROJECT_BOOK_WITH_UI.getUserPrefs();
        assertEquals(userPrefs, USER_PREFS);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(PROJECT_BOOK_WITH_UI.equals(PROJECT_BOOK_WITH_UI));

        // different types -> returns false
        assertFalse(PROJECT_BOOK_WITH_UI.equals("1"));

        // null -> return false
        assertFalse(PROJECT_BOOK_WITH_UI.equals(null));

        //same viewMode, project, predicate, comparator -> return true
        ProjectBookWithUi sameProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                VALID_PROJECT, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, USER_PREFS);
        assertTrue(PROJECT_BOOK_WITH_UI.equals(sameProjectbookWithUi));

        //same viewMode, both projects null, same predicate, same comparator -> return true
        ProjectBookWithUi nullProjectBookWithUi =
                new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS, null,
                        Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, USER_PREFS);
        sameProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                null, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, USER_PREFS);
        assertTrue(nullProjectBookWithUi.equals(sameProjectbookWithUi));

        // different viewMode -> return false
        ProjectBookWithUi differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.PROJECTS,
                VALID_PROJECT, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, USER_PREFS);
        assertFalse(PROJECT_BOOK_WITH_UI.equals(differentProjectbookWithUi));

        // different project -> returns false
        Project otherProject = new ProjectBuilder().withName("OTHER").build();
        differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                otherProject, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, USER_PREFS);
        assertFalse(PROJECT_BOOK_WITH_UI.equals(differentProjectbookWithUi));

        // different tags visibility -> returns false
        differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                VALID_PROJECT, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, false, USER_PREFS);
        assertFalse(PROJECT_BOOK_WITH_UI.equals(differentProjectbookWithUi));

        //        // different predicate -> returns false
        //        Predicate<TrackedItem> completePredicate = new CompletionStatusPredicate(Arrays.asList("incomplete"));
        //        differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
        //                otherProject, completePredicate, nameCompare);
        //        assertFalse(projectBookWithUi.equals(differentProjectbookWithUi));

        // different user prefs -> returns false
        ReadOnlyUserPrefs otherUserPrefs =
            new UserPrefs().returnChangedGuiThemeSettings(new GuiThemeSettings(new Theme(Theme.ThemeType.LIGHT)));
        differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
            VALID_PROJECT, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, NAME_COMPARE, true, otherUserPrefs);
        assertFalse(PROJECT_BOOK_WITH_UI.equals(differentProjectbookWithUi));
    }
}
