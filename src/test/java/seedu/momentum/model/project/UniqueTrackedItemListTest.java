package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.BOB;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.exceptions.DuplicateTrackableItemException;
import seedu.momentum.model.project.exceptions.TrackableItemNotFoundException;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TypicalProjectsOrders;

public class UniqueTrackedItemListTest {

    private final UniqueTrackedItemList uniqueTrackedItemList = new UniqueTrackedItemList();

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueTrackedItemList.contains(ALICE));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueTrackedItemList.add(ALICE);
        assertTrue(uniqueTrackedItemList.contains(ALICE));
    }

    @Test
    public void contains_projectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTrackedItemList.add(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueTrackedItemList.contains(editedAlice));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateProjectException() {
        uniqueTrackedItemList.add(ALICE);
        assertThrows(DuplicateTrackableItemException.class, () -> uniqueTrackedItemList.add(ALICE));
    }

    @Test
    public void setProject_nullTargetProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.setTrackedItem(null, ALICE));
    }

    @Test
    public void setProject_nullEditedProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.setTrackedItem(ALICE, null));
    }

    @Test
    public void setProject_targetProjectNotInList_throwsProjectNotFoundException() {
        assertThrows(TrackableItemNotFoundException.class, () -> uniqueTrackedItemList.setTrackedItem(ALICE, ALICE));
    }

    @Test
    public void setProject_editedProjectIsSameProject_success() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.setTrackedItem(ALICE, ALICE);
        UniqueTrackedItemList expectedUniqueTrackedItemList = new UniqueTrackedItemList();
        expectedUniqueTrackedItemList.add(ALICE);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProject_editedProjectHasSameIdentity_success() {
        uniqueTrackedItemList.add(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueTrackedItemList.setTrackedItem(ALICE, editedAlice);
        UniqueTrackedItemList expectedUniqueTrackedItemList = new UniqueTrackedItemList();
        expectedUniqueTrackedItemList.add(editedAlice);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProject_editedProjectHasDifferentIdentity_success() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.setTrackedItem(ALICE, BOB);
        UniqueTrackedItemList expectedUniqueTrackedItemList = new UniqueTrackedItemList();
        expectedUniqueTrackedItemList.add(BOB);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProject_editedProjectHasNonUniqueIdentity_throwsDuplicateProjectException() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.add(BOB);
        assertThrows(DuplicateTrackableItemException.class, () -> uniqueTrackedItemList.setTrackedItem(ALICE, BOB));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(TrackableItemNotFoundException.class, () -> uniqueTrackedItemList.remove(ALICE));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.remove(ALICE);
        UniqueTrackedItemList expectedUniqueTrackedItemList = new UniqueTrackedItemList();
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueTrackedItemList.setTrackedItems((UniqueTrackedItemList) null));
    }

    @Test
    public void setProjects_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueTrackedItemList.add(ALICE);
        UniqueTrackedItemList expectedUniqueTrackedItemList = new UniqueTrackedItemList();
        expectedUniqueTrackedItemList.add(BOB);
        uniqueTrackedItemList.setTrackedItems(expectedUniqueTrackedItemList);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.setTrackedItems((List<TrackedItem>) null));
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueTrackedItemList.add(ALICE);
        List<TrackedItem> trackedItemList = Collections.singletonList(BOB);
        uniqueTrackedItemList.setTrackedItems(trackedItemList);
        UniqueTrackedItemList expectedUniqueTrackedItemList = new UniqueTrackedItemList();
        expectedUniqueTrackedItemList.add(BOB);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateProjectException() {
        List<TrackedItem> listWithDuplicateProjects = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateTrackableItemException.class, () ->
            uniqueTrackedItemList.setTrackedItems(listWithDuplicateProjects));
    }

    @Test
    public void setOrder_nullSortType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.setOrder(null, true, true));
    }

    @Test
    public void setOrder_alphabeticalAscendingSortType_sortsListAlphabeticallyAscending() {
        for (Project p : getTypicalProjects()) {
            uniqueTrackedItemList.add(p);
        }
        uniqueTrackedItemList.setOrder(SortType.ALPHA, true, false);
        UniqueTrackedItemList expectedUniqueTrackedItemList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByAlphabeticalAscending());
        assertEquals(uniqueTrackedItemList, expectedUniqueTrackedItemList);

    }

    @Test
    public void setOrder_alphabeticalDescendingSortType_sortsListAlphabeticallyDescending() {
        for (Project p : getTypicalProjects()) {
            uniqueTrackedItemList.add(p);
        }
        uniqueTrackedItemList.setOrder(SortType.ALPHA, false, false);
        UniqueTrackedItemList expectedUniqueTrackedItemList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByAlphabeticalDescending());
        assertEquals(uniqueTrackedItemList, expectedUniqueTrackedItemList);

    }

    @Test
    public void setOrder_deadlineAscendingSortType_sortsListDeadlineAscending() {
        for (Project p : getTypicalProjects()) {
            uniqueTrackedItemList.add(p);
        }
        uniqueTrackedItemList.setOrder(SortType.DEADLINE, true, false);
        UniqueTrackedItemList expectedUniqueTrackedItemList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByDeadlineAscending());
        assertEquals(uniqueTrackedItemList, expectedUniqueTrackedItemList);

    }

    @Test
    public void setOrder_deadlineDescendingSortType_sortsListDeadlineDescending() {
        for (Project p : getTypicalProjects()) {
            uniqueTrackedItemList.add(p);
        }
        uniqueTrackedItemList.setOrder(SortType.DEADLINE, false, false);
        UniqueTrackedItemList expectedUniqueTrackedItemList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByDeadlineDescending());
        assertEquals(uniqueTrackedItemList, expectedUniqueTrackedItemList);

    }

    @Test
    public void setOrder_createdDateAscendingSortType_sortsListCreatedDateAscending() {
        for (Project p : getTypicalProjects()) {
            uniqueTrackedItemList.add(p);
        }
        uniqueTrackedItemList.setOrder(SortType.CREATED, true, false);
        UniqueTrackedItemList expectedUniqueTrackedItemList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByCreatedDateAscending());
        assertEquals(uniqueTrackedItemList, expectedUniqueTrackedItemList);
    }

    @Test
    public void setOrder_createdDateDescendingSortType_sortsListCreatedDateDescending() {
        for (Project p : getTypicalProjects()) {
            uniqueTrackedItemList.add(p);
        }
        uniqueTrackedItemList.setOrder(SortType.CREATED, false, false);
        UniqueTrackedItemList expectedUniqueTrackedItemList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByCreatedDateDescending());
        assertEquals(uniqueTrackedItemList, expectedUniqueTrackedItemList);

    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTrackedItemList.asUnmodifiableObservableList().remove(0));
    }
}