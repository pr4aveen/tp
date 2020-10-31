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

import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.model.project.exceptions.DuplicateItemException;
import seedu.momentum.model.project.exceptions.DuplicateTrackableItemException;
import seedu.momentum.model.project.exceptions.ItemNotFoundException;
import seedu.momentum.model.project.exceptions.TrackableItemNotFoundException;
import seedu.momentum.model.timer.WorkDuration;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TypicalProjectsOrders;

public class UniqueTrackedItemListTest {

    private final UniqueItemList<TrackedItem> uniqueTrackedItemList = new UniqueItemList<>();

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
        assertThrows(DuplicateItemException.class, () -> uniqueTrackedItemList.add(ALICE));
    }

    @Test
    public void setProject_nullTargetProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.set(null, ALICE));
    }

    @Test
    public void setProject_nullEditedProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.set(ALICE, null));
    }

    @Test
    public void setProject_targetProjectNotInList_throwsProjectNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueTrackedItemList.set(ALICE, ALICE));
    }

    @Test
    public void setProject_editedProjectIsSameProject_success() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.set(ALICE, ALICE);
        UniqueItemList<TrackedItem> expectedUniqueTrackedItemList = new UniqueItemList<>();
        expectedUniqueTrackedItemList.add(ALICE);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProject_editedProjectHasSameIdentity_success() {
        uniqueTrackedItemList.add(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueTrackedItemList.set(ALICE, editedAlice);
        UniqueItemList<TrackedItem> expectedUniqueTrackedItemList = new UniqueItemList<>();
        expectedUniqueTrackedItemList.add(editedAlice);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProject_editedProjectHasDifferentIdentity_success() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.set(ALICE, BOB);
        UniqueItemList<TrackedItem> expectedUniqueTrackedItemList = new UniqueItemList<>();
        expectedUniqueTrackedItemList.add(BOB);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProject_editedProjectHasNonUniqueIdentity_throwsDuplicateProjectException() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.add(BOB);
        assertThrows(DuplicateItemException.class, () -> uniqueTrackedItemList.set(ALICE, BOB));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueTrackedItemList.remove(ALICE));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueTrackedItemList.add(ALICE);
        uniqueTrackedItemList.remove(ALICE);
        UniqueItemList<TrackedItem> expectedUniqueTrackedItemList = new UniqueItemList();
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueTrackedItemList.setItems((UniqueItemList<TrackedItem>) null));
    }

    @Test
    public void setProjects_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueTrackedItemList.add(ALICE);
        UniqueItemList<TrackedItem> expectedUniqueTrackedItemList = new UniqueItemList<>();
        expectedUniqueTrackedItemList.add(BOB);
        uniqueTrackedItemList.setItems(expectedUniqueTrackedItemList);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTrackedItemList.setItems((List<TrackedItem>) null));
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueTrackedItemList.add(ALICE);
        List<TrackedItem> trackedItemList = Collections.singletonList(BOB);
        uniqueTrackedItemList.setItems(trackedItemList);
        UniqueItemList<TrackedItem> expectedUniqueTrackedItemList = new UniqueItemList<>();
        expectedUniqueTrackedItemList.add(BOB);
        assertEquals(expectedUniqueTrackedItemList, uniqueTrackedItemList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateProjectException() {
        List<TrackedItem> listWithDuplicateProjects = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateItemException.class, () ->
            uniqueTrackedItemList.setItems(listWithDuplicateProjects));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTrackedItemList.asUnmodifiableObservableList().remove(0));
    }
}
