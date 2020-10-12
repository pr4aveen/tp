package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

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
import seedu.momentum.model.project.exceptions.DuplicateProjectException;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.testutil.ProjectBuilder;

public class ProjectBookTest {

    private final ProjectBook projectBook = new ProjectBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), projectBook.getProjectList());
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

        assertThrows(DuplicateProjectException.class, () -> projectBook.resetData(newData));
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectBook.hasProject(null));
    }

    @Test
    public void hasProject_projectNotInProjectBook_returnsFalse() {
        assertFalse(projectBook.hasProject(ALICE));
    }

    @Test
    public void hasProject_projectInProjectBook_returnsTrue() {
        projectBook.addProject(ALICE);
        assertTrue(projectBook.hasProject(ALICE));
    }

    @Test
    public void hasProject_projectWithSameIdentityFieldsInProjectBook_returnsTrue() {
        projectBook.addProject(ALICE);
        Project editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(projectBook.hasProject(editedAlice));
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> projectBook.getProjectList().remove(0));
    }

    /**
     * A stub ReadOnlyProjectBook whose projects list can violate interface constraints.
     */
    private static class ProjectBookStub implements ReadOnlyProjectBook {
        private final ObservableList<Project> projects = FXCollections.observableArrayList();

        ProjectBookStub(Collection<Project> projects) {
            this.projects.setAll(projects);
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return projects;
        }

        @Override
        public Set<Tag> getProjectTags() {
            Set<Tag> tags = new HashSet<>();
            getProjectList().forEach(project -> tags.addAll(project.getTags()));
            return tags;
        }
    }

}
