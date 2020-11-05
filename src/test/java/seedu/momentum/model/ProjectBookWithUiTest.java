package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.model.project.predicates.CompletionStatusPredicate;
import seedu.momentum.testutil.ProjectBuilder;

public class ProjectBookWithUiTest {

    private static final Project validProject = new ProjectBuilder().withName("TEST").build();
    private static final Comparator<TrackedItem> nameCompare = new NameCompare();

    private static final ProjectBookWithUi projectBookWithUi =
            new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS, validProject,
                    Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);

    @Test
    public void constructor() {
        assertEquals(ViewMode.TASKS, projectBookWithUi.getViewMode());
        assertEquals(validProject, projectBookWithUi.getProject());
        assertEquals(Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, projectBookWithUi.getPredicate());
        assertEquals(nameCompare, projectBookWithUi.getComparator());
    }

    @Test
    public void getViewMode_returnsViewMode() {
        ViewMode viewMode = projectBookWithUi.getViewMode();
        assertEquals(viewMode, ViewMode.TASKS);
    }

    @Test
    public void getProject_returnsProject() {
        Project project = projectBookWithUi.getProject();
        assertEquals(project, validProject);
    }

    @Test
    public void getPredicate_returnsPredicate() {
        Predicate<TrackedItem> predicate = projectBookWithUi.getPredicate();
        assertEquals(predicate, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Test
    public void getComparator_returnsComparator() {
        Comparator<TrackedItem> comparator = projectBookWithUi.getComparator();
        assertEquals(comparator, nameCompare);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(projectBookWithUi.equals(projectBookWithUi));

        // different types -> returns false
        assertFalse(projectBookWithUi.equals("1"));

        // null -> return false
        assertFalse(projectBookWithUi.equals(null));

        //same viewMode, project, predicate, comparator -> return true
        ProjectBookWithUi sameProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                validProject, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);
        assertTrue(projectBookWithUi.equals(sameProjectbookWithUi));

        //same viewMode, both projects null, same predicate, same comparator -> return true
        ProjectBookWithUi nullProjectBookWithUi =
                new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS, null,
                        Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);
        sameProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                null, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);
        assertTrue(nullProjectBookWithUi.equals(sameProjectbookWithUi));

        // different viewMode -> return false
        ProjectBookWithUi differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.PROJECTS,
                validProject, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);
        assertFalse(projectBookWithUi.equals(differentProjectbookWithUi));

        // different project -> returns false
        Project otherProject = new ProjectBuilder().withName("OTHER").build();
        differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
                otherProject, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);
        assertFalse(projectBookWithUi.equals(differentProjectbookWithUi));

//        // different predicate -> returns false
//        Predicate<TrackedItem> completePredicate = new CompletionStatusPredicate(Arrays.asList("incomplete"));
//        differentProjectbookWithUi = new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS,
//                otherProject, completePredicate, nameCompare);
//        assertFalse(projectBookWithUi.equals(differentProjectbookWithUi));
    }
}
