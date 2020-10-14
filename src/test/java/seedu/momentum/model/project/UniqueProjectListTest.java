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

import seedu.momentum.model.project.exceptions.DuplicateProjectException;
import seedu.momentum.model.project.exceptions.ProjectNotFoundException;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TypicalProjectsOrders;

public class UniqueProjectListTest {

    private final UniqueProjectList uniqueProjectList = new UniqueProjectList();

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueProjectList.contains(ALICE));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueProjectList.add(ALICE);
        assertTrue(uniqueProjectList.contains(ALICE));
    }

    @Test
    public void contains_projectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueProjectList.add(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueProjectList.contains(editedAlice));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateProjectException() {
        uniqueProjectList.add(ALICE);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.add(ALICE));
    }

    @Test
    public void setProject_nullTargetProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProject(null, ALICE));
    }

    @Test
    public void setProject_nullEditedProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProject(ALICE, null));
    }

    @Test
    public void setProject_targetProjectNotInList_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.setProject(ALICE, ALICE));
    }

    @Test
    public void setProject_editedProjectIsSameProject_success() {
        uniqueProjectList.add(ALICE);
        uniqueProjectList.setProject(ALICE, ALICE);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(ALICE);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasSameIdentity_success() {
        uniqueProjectList.add(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueProjectList.setProject(ALICE, editedAlice);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(editedAlice);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasDifferentIdentity_success() {
        uniqueProjectList.add(ALICE);
        uniqueProjectList.setProject(ALICE, BOB);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(BOB);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasNonUniqueIdentity_throwsDuplicateProjectException() {
        uniqueProjectList.add(ALICE);
        uniqueProjectList.add(BOB);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.setProject(ALICE, BOB));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.remove(ALICE));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueProjectList.add(ALICE);
        uniqueProjectList.remove(ALICE);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProjects((UniqueProjectList) null));
    }

    @Test
    public void setProjects_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueProjectList.add(ALICE);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(BOB);
        uniqueProjectList.setProjects(expectedUniqueProjectList);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProjects((List<Project>) null));
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueProjectList.add(ALICE);
        List<Project> projectList = Collections.singletonList(BOB);
        uniqueProjectList.setProjects(projectList);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(BOB);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateProjectException() {
        List<Project> listWithDuplicateProjects = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.setProjects(listWithDuplicateProjects));
    }

    @Test
    public void setOrder_nullSortType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setOrder(null, true));
    }

    @Test
    public void setOrder_alphabeticalAscendingSortType_sortsListAlphabeticallyAscending() {
        for (Project p : getTypicalProjects()) {
            uniqueProjectList.add(p);
        }
        uniqueProjectList.setOrder(SortType.ALPHA, true);
        UniqueProjectList expectedUniqueProjectList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByAlphabeticalAscending());
        assertEquals(uniqueProjectList, expectedUniqueProjectList);

    }

    @Test
    public void setOrder_alphabeticalDescendingSortType_sortsListAlphabeticallyDescending() {
        for (Project p : getTypicalProjects()) {
            uniqueProjectList.add(p);
        }
        uniqueProjectList.setOrder(SortType.ALPHA, false);
        UniqueProjectList expectedUniqueProjectList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByAlphabeticalDescending());
        assertEquals(uniqueProjectList, expectedUniqueProjectList);

    }

    @Test
    public void setOrder_deadlineAscendingSortType_sortsListDeadlineAscending() {
        for (Project p : getTypicalProjects()) {
            uniqueProjectList.add(p);
        }
        uniqueProjectList.setOrder(SortType.DEADLINE, true);
        UniqueProjectList expectedUniqueProjectList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByDeadlineAscending());
        assertEquals(uniqueProjectList, expectedUniqueProjectList);

    }

    @Test
    public void setOrder_deadlineDescendingSortType_sortsListDeadlineDescending() {
        for (Project p : getTypicalProjects()) {
            uniqueProjectList.add(p);
        }
        uniqueProjectList.setOrder(SortType.DEADLINE, false);
        UniqueProjectList expectedUniqueProjectList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByDeadlineDescending());
        assertEquals(uniqueProjectList, expectedUniqueProjectList);

    }

    @Test
    public void setOrder_createdDateAscendingSortType_sortsListCreatedDateAscending() {
        for (Project p : getTypicalProjects()) {
            uniqueProjectList.add(p);
        }
        uniqueProjectList.setOrder(SortType.CREATED, true);
        UniqueProjectList expectedUniqueProjectList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByCreatedDateAscending());
        assertEquals(uniqueProjectList, expectedUniqueProjectList);

    }

    @Test
    public void setOrder_createdDateDescendingSortType_sortsListCreatedDateDescending() {
        for (Project p : getTypicalProjects()) {
            uniqueProjectList.add(p);
        }
        uniqueProjectList.setOrder(SortType.CREATED, false);
        UniqueProjectList expectedUniqueProjectList = TypicalProjectsOrders
                .getUniqueProjectList(TypicalProjectsOrders.getOrderedProjectBookByCreatedDateDescending());
        assertEquals(uniqueProjectList, expectedUniqueProjectList);

    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueProjectList.asUnmodifiableObservableList().remove(0));
    }
}
