package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_REMINDER_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;
import static seedu.momentum.testutil.TypicalProjectsOrders.getOrderedProjectBookByDeadlineAscending;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.exceptions.DuplicateTrackableItemException;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.testutil.ProjectBuilder;

public class ProjectBookTest {

    private final ProjectBook projectBook = new ProjectBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), projectBook.getTrackedItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyProjectBook_replacesData() {
        ProjectBook newData = getTypicalProjectBook();
        projectBook.resetData(newData);
        assertEquals(newData, projectBook);
    }

    @Test
    public void resetData_withDuplicateProjects_throwsDuplicateProjectException() {
        // Two projects with the same identity fields
        Project editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Project> newProjects = Arrays.asList(ALICE, editedAlice);
        ProjectBookStub newData = new ProjectBookStub(newProjects);

        assertThrows(DuplicateTrackableItemException.class, () -> projectBook.resetData(newData));
    }

    @Test
    public void setOrder_withSortTypeNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectBook.setOrder(null, true, true));
    }

    @Test
    public void setOrder_withValidSortType_ordersProjectBook() {
        ProjectBook unorderedProjectBook = getTypicalProjectBook();
        ProjectBook orderedProjectBook = new ProjectBook();
        orderedProjectBook.setTrackedItems(getOrderedProjectBookByDeadlineAscending());
        unorderedProjectBook.setOrder(SortType.DEADLINE, true, false);
        assertEquals(orderedProjectBook, unorderedProjectBook);
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectBook.hasTrackedItem(null));
    }

    @Test
    public void hasProject_projectNotInProjectBook_returnsFalse() {
        assertFalse(projectBook.hasTrackedItem(ALICE));
    }

    @Test
    public void hasProject_projectInProjectBook_returnsTrue() {
        projectBook.addTrackedItem(ALICE);
        assertTrue(projectBook.hasTrackedItem(ALICE));
    }

    @Test
    public void hasProject_projectWithSameIdentityFieldsInProjectBook_returnsTrue() {
        projectBook.addTrackedItem(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(projectBook.hasTrackedItem(editedAlice));
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> projectBook.getTrackedItemList().remove(0));
    }

    @Test
    public void removeReminder() {
        Project editedAlice =
                new ProjectBuilder(ALICE).withReminder(VALID_REMINDER_AMY, VALID_CREATED_DATE_AMY).build();
        editedAlice = editedAlice.removeReminder();
        projectBook.addTrackedItem(editedAlice);
        ProjectBook expectedProjectBook = new ProjectBook();
        expectedProjectBook.addTrackedItem(new ProjectBuilder(ALICE).withEmptyReminder().build());
        assertTrue(expectedProjectBook.equals(projectBook));
    }

    /**
     * A stub ReadOnlyProjectBook whose projects list can violate interface constraints.
     */
    private static class ProjectBookStub implements ReadOnlyProjectBook {
        private final ObservableList<TrackedItem> trackedItems = FXCollections.observableArrayList();

        ProjectBookStub(Collection<Project> trackedItems) {
            this.trackedItems.setAll(trackedItems);
        }

        @Override
        public ObservableList<TrackedItem> getTrackedItemList() {
            return trackedItems;
        }

        @Override
        public Set<Tag> getTrackedItemTags() {
            Set<Tag> tags = new HashSet<>();
            getTrackedItemList().forEach(project -> tags.addAll(project.getTags()));
            return tags;
        }
    }

}
