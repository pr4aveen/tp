package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.model.project.predicates.CompletionStatusPredicate;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.MomentumPredicate;
import seedu.momentum.testutil.ProjectBuilder;

public class VersionedProjectBookTest {

    private static final ReadOnlyProjectBook PROJECT_BOOK = new ProjectBook();
    private static final Project TEST_PROJECT = new ProjectBuilder().withName("TEST").build();
    private static final Project AFTER_PROJECT = new ProjectBuilder().withName("AFTER").build();
    private static final ViewMode INIT_VIEWMODE = ViewMode.TASKS;
    private static final ViewMode AFTER_VIEWMODE = ViewMode.PROJECTS;
    private static final MomentumPredicate INIT_PREDICATE = Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;
    private static final MomentumPredicate AFTER_PREDICATE =
            new CompletionStatusPredicate(FindType.ALL, Arrays.asList("incomplete"));
    private static final Comparator<TrackedItem> INIT_COMPARE = new NameCompare();
    private static final Comparator<TrackedItem> AFTER_COMPARE = new CreatedDateCompare();
    private static final boolean INIT_TAGS_VISIBLE = true;
    private static final boolean AFTER_TAGS_VISIBLE = false;
    private static final ReadOnlyUserPrefs INIT_USER_PREFS = new UserPrefs();
    private static final ReadOnlyUserPrefs AFTER_USER_PREFS =
        new UserPrefs().returnChangedGuiThemeSettings(new GuiThemeSettings(new Theme(Theme.ThemeType.LIGHT)));

    private final VersionedProjectBook versionedProjectBook = new VersionedProjectBook(PROJECT_BOOK, INIT_VIEWMODE,
            TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE, INIT_TAGS_VISIBLE, INIT_USER_PREFS);

    @Test
    public void constructor() {
        assertEquals(INIT_VIEWMODE, versionedProjectBook.getCurrentViewMode());
        assertEquals(TEST_PROJECT, versionedProjectBook.getCurrentProject());
        assertEquals(INIT_PREDICATE, versionedProjectBook.getCurrentPredicate());
        assertEquals(INIT_COMPARE, versionedProjectBook.getCurrentComparator());
        assertEquals(INIT_TAGS_VISIBLE, versionedProjectBook.isTagsVisible());
        assertEquals(0, versionedProjectBook.getCurrentStatePointer());
        assertEquals(new ProjectBookWithUi(PROJECT_BOOK, INIT_VIEWMODE, TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE,
                INIT_TAGS_VISIBLE, INIT_USER_PREFS), versionedProjectBook.getCurrentProjectBookWithUi());
    }

    /**
     * Committing versionedProjectBook when there is no history log to redo does not need to flush history.
     * Successfully resets versionedProjectBook to previous commit.
     */
    @Test
    public void commit_noNeedFlush_success() {
        ProjectBookWithUi toAdd = new ProjectBookWithUi(versionedProjectBook, AFTER_VIEWMODE, AFTER_PROJECT,
                AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE, AFTER_USER_PREFS);
        List<ProjectBookWithUi> expectedStateList = versionedProjectBook.getProjectBookStateList();

        expectedStateList.add(toAdd);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);
        assertEquals(versionedProjectBook.getProjectBookStateList(), expectedStateList);
        assertEquals(versionedProjectBook.getCurrentViewMode(), AFTER_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), AFTER_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), AFTER_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), AFTER_PREDICATE);
        assertEquals(versionedProjectBook.isTagsVisible(), AFTER_TAGS_VISIBLE);
        assertEquals(versionedProjectBook.getUserPrefs(), AFTER_USER_PREFS);
    }

    /**
     * Committing versionedProjectBook when there is a history log to redo flushes history.
     * Successfully resets versionedProjectBook to previous commit.
     */
    @Test
    public void commit_flush_success() {
        ProjectBookWithUi toAdd = new ProjectBookWithUi(versionedProjectBook, AFTER_VIEWMODE, AFTER_PROJECT,
                AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE, AFTER_USER_PREFS);
        List<ProjectBookWithUi> expectedStateList = versionedProjectBook.getProjectBookStateList();

        expectedStateList.add(toAdd);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        versionedProjectBook.undo();
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);
        assertEquals(versionedProjectBook.getProjectBookStateList(), expectedStateList);
        assertEquals(versionedProjectBook.getCurrentViewMode(), AFTER_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), AFTER_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), AFTER_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), AFTER_PREDICATE);
        assertEquals(versionedProjectBook.isTagsVisible(), AFTER_TAGS_VISIBLE);
        assertEquals(versionedProjectBook.getUserPrefs(), AFTER_USER_PREFS);
    }

    @Test
    public void undo() {

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        // To shift pointer to be able to undo command
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);

        versionedProjectBook.undo();
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        // Check with original state of versionedProjectBook
        assertEquals(versionedProjectBook.getCurrentViewMode(), INIT_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), TEST_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), INIT_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), INIT_PREDICATE);
        assertEquals(versionedProjectBook.isTagsVisible(), INIT_TAGS_VISIBLE);
        assertEquals(versionedProjectBook.getUserPrefs(), INIT_USER_PREFS);
    }

    @Test
    public void redo() {

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        // To shift pointer to be able to undo then redo command
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);

        versionedProjectBook.undo();
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        versionedProjectBook.redo();
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);

        // Check with original state of versionedProjectBook
        assertEquals(versionedProjectBook.getCurrentViewMode(), AFTER_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), AFTER_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), AFTER_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), AFTER_PREDICATE);
        assertEquals(versionedProjectBook.isTagsVisible(), AFTER_TAGS_VISIBLE);
        assertEquals(versionedProjectBook.getUserPrefs(), AFTER_USER_PREFS);
    }

    @Test
    public void canUndoCommand_cannotUndo_returnFalse() {
        // after initializing versionedProjectBook (no history log)
        assertFalse(versionedProjectBook.canUndoCommand());
    }

    @Test
    public void canUndoCommand_canUndo_returnTrue() {
        // after initializing versionedProjectBook (no history log)
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        assertTrue(versionedProjectBook.canUndoCommand());
    }

    @Test
    public void getProjectBookStateList() {
        List<ProjectBookWithUi> expectedStateList = versionedProjectBook.getProjectBookStateList();
        expectedStateList.add(new ProjectBookWithUi(versionedProjectBook, AFTER_VIEWMODE, AFTER_PROJECT,
                AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE, AFTER_USER_PREFS));
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        assertEquals(versionedProjectBook.getProjectBookStateList(), expectedStateList);
    }

    @Test
    public void getCurrentStatePointer() {
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);
    }

    @Test
    public void getCurrentProjectBookWithUi() {
        ProjectBookWithUi expectedProjectBook = new ProjectBookWithUi(
                versionedProjectBook, INIT_VIEWMODE, TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE, INIT_TAGS_VISIBLE,
            INIT_USER_PREFS);
        assertEquals(versionedProjectBook.getCurrentProjectBookWithUi(), expectedProjectBook);
    }

    @Test
    public void getCurrentViewMode() {
        assertEquals(versionedProjectBook.getCurrentViewMode(), INIT_VIEWMODE);
    }

    @Test
    public void getCurrentProject() {
        assertEquals(versionedProjectBook.getCurrentProject(), TEST_PROJECT);
    }

    @Test
    public void getCurrentPredicate() {
        assertEquals(versionedProjectBook.getCurrentPredicate(), INIT_PREDICATE);
    }

    @Test
    public void getCurrentComparator() {
        assertEquals(versionedProjectBook.getCurrentComparator(), INIT_COMPARE);
    }

    @Test
    public void getCurrentIsTagsVisible() {
        assertEquals(versionedProjectBook.isTagsVisible(), INIT_TAGS_VISIBLE);
    }

    @Test
    public void getCurrentUserPrefs() {
        assertEquals(versionedProjectBook.getUserPrefs(), INIT_USER_PREFS);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(versionedProjectBook.equals(versionedProjectBook));

        // different types -> returns false
        assertFalse(versionedProjectBook.equals("1"));

        // null -> return false
        assertFalse(versionedProjectBook.equals(null));

        // different project book -> return false
        VersionedProjectBook other = new VersionedProjectBook(PROJECT_BOOK, AFTER_VIEWMODE,
                AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE, AFTER_USER_PREFS);
        assertFalse(versionedProjectBook.equals(other));

        // same projectBookStateList and currentStatePointer -> returns true
        VersionedProjectBook expectedProjectBook = new VersionedProjectBook(PROJECT_BOOK, INIT_VIEWMODE,
                TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE, INIT_TAGS_VISIBLE, INIT_USER_PREFS);
        expectedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE, AFTER_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        assertTrue(versionedProjectBook.equals(expectedProjectBook));

        // different projectBookStateList and currentStatePointer -> returns false
        // projectBookStateList and currentStatePointer will be changed together
        expectedProjectBook.commit(INIT_VIEWMODE, TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE, INIT_TAGS_VISIBLE,
            AFTER_USER_PREFS);
        assertFalse(versionedProjectBook.equals(expectedProjectBook));
    }
}
