package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.testutil.ProjectBuilder;

public class VersionedProjectBookTest {

    private static final ReadOnlyProjectBook projectBook = new ProjectBook();
    private static final Project TEST_PROJECT = new ProjectBuilder().withName("TEST").build();
    private static final Project AFTER_PROJECT = new ProjectBuilder().withName("AFTER").build();
    private static final ViewMode INIT_VIEWMODE = ViewMode.TASKS;
    private static final ViewMode AFTER_VIEWMODE = ViewMode.PROJECTS;
    private static final Predicate<TrackedItem> INIT_PREDICATE = Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;
    private static final Predicate<TrackedItem> AFTER_PREDICATE = Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;
    private static final Comparator<TrackedItem> INIT_COMPARE = new NameCompare();
    private static final Comparator<TrackedItem> AFTER_COMPARE = new CreatedDateCompare();

    private final VersionedProjectBook versionedProjectBook = new VersionedProjectBook(projectBook, INIT_VIEWMODE,
            TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE);

    /**
     * Committing versionedProjectBook when there is no history log to redo does not need to flush history.
     * Successfully resets versionedProjectBook to previous commit.
     */
    @Test
    public void commit_noNeedFlush_success() {
        ProjectBookWithUi toAdd = new ProjectBookWithUi(
                versionedProjectBook, AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
        List<ProjectBookWithUi> expectedStateList = versionedProjectBook.getProjectBookStateList();

        expectedStateList.add(toAdd);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);
        assertEquals(versionedProjectBook.getProjectBookStateList(), expectedStateList);
        assertEquals(versionedProjectBook.getCurrentViewMode(), AFTER_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), AFTER_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), AFTER_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), AFTER_PREDICATE);
    }

    /**
     * Committing versionedProjectBook when there is a history log to redo flushes history.
     * Successfully resets versionedProjectBook to previous commit.
     */
    @Test
    public void commit_flush_success() {
        ProjectBookWithUi toAdd = new ProjectBookWithUi(
                versionedProjectBook, AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
        List<ProjectBookWithUi> expectedStateList = versionedProjectBook.getProjectBookStateList();

        expectedStateList.add(toAdd);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
        versionedProjectBook.undo();
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);
        assertEquals(versionedProjectBook.getProjectBookStateList(), expectedStateList);
        assertEquals(versionedProjectBook.getCurrentViewMode(), AFTER_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), AFTER_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), AFTER_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), AFTER_PREDICATE);
    }

    @Test
    public void undo() {

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        // To shift pointer to be able to undo command
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 1);

        versionedProjectBook.undo();
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        // Check with original state of versionedProjectBook
        assertEquals(versionedProjectBook.getCurrentViewMode(), INIT_VIEWMODE);
        assertEquals(versionedProjectBook.getCurrentProject(), TEST_PROJECT);
        assertEquals(versionedProjectBook.getCurrentComparator(), INIT_COMPARE);
        assertEquals(versionedProjectBook.getCurrentPredicate(), INIT_PREDICATE);
    }

    @Test
    public void redo() {

        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);

        // To shift pointer to be able to undo then redo command
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
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
    }

    @Test
    public void canUndoCommand_cannotUndo_returnFalse() {
        // after initializing versionedProjectBook (no history log)
        assertFalse(versionedProjectBook.canUndoCommand());
    }

    @Test
    public void canUndoCommand_canUndo_returnTrue() {
        // after initializing versionedProjectBook (no history log)
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
        assertTrue(versionedProjectBook.canUndoCommand());
    }

    @Test
    public void getProjectBookStateList() {
        List<ProjectBookWithUi> expectedStateList = versionedProjectBook.getProjectBookStateList();
        expectedStateList.add(new ProjectBookWithUi(
                versionedProjectBook, AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE));
        versionedProjectBook.commit(AFTER_VIEWMODE, AFTER_PROJECT, AFTER_PREDICATE, AFTER_COMPARE);
        assertEquals(versionedProjectBook.getProjectBookStateList(), expectedStateList);
    }

    @Test
    public void getCurrentStatePointer() {
        assertEquals(versionedProjectBook.getCurrentStatePointer(), 0);
    }

    @Test
    public void getCurrentProjectBookWithUi() {
        ProjectBookWithUi expectedProjectBook = new ProjectBookWithUi(
                versionedProjectBook, INIT_VIEWMODE, TEST_PROJECT, INIT_PREDICATE, INIT_COMPARE);
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
    public void equals() {

    }
}
